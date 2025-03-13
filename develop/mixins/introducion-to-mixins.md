---
title: Introduction to Mixins
description: Learn about the purpose of mixins and gain an intuitive understanding of how they work.
authors:
  - ArkoSammy12
---

# Introduction to Mixins {#introduction-to-mixins}

The Fabric API provides a lot of useful hooks and other utilities to perform a variety of tasks in a safe and compatible way, be it to run code when the server shuts down, register commands, or rendering stuff.

Sometimes, however, Fabric API, or the Minecraft code itself, will not provide the tools necessary to accomplish a certain goal. Perhaps you would you like a callback that is invoked whenever an explosion happens, or you would like to modify the logic of mob spawning. In these cases, you will need to directly **modify the source code of the game**.

This is where **Mixins** come into play. Mixins are a set of powerful tools designed to allow you to more easily modify the code of the game, in the form of so called "Mixin classes". Later we will learn how to setup and write our first mixin class, but you should first know what exactly happens when you create a mixin. 

# What exactly is a "Mixin class"? {#what-exactly-is-a-mixin-class?}

Let's begin with the simplest case. Consider a regular Java class with some fields and methods:

```java

public class Person {
    
    private final String name;
    private final int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
    
}
```

Now consider a mixin class that targets this `Person` class:

```java

@Mixin(Person.class)
public abstract class PersonMixin {
    
    
    
}

```

By annotating `PersonMixin` with `@Mixin()`, we have turned this regular Java class into a mixin class.
Mixin classes behave a little differently from regular Java classes. 

What a mixin class does, essentially, is that, whenever the game starts (runtime), the mixin class will grab all of its members, including fields and methods, and merge them with the mixin class' target class.

For example, if we add a `sayHello()` method, and an integer `phoneNumber` field to the `PersonMixin` class like so:

```java

@Mixin(Person.class)
public abstract class PersonMixin {
    
    private int phoneNumber;
    
    public void sayHello() {
        System.out.println("Hello Fabric!");
    }
    
}

```

This `sayHello()` method will be merged with the `Person` class **at runtime**, such that the class will end up looking like this:


```java

public class Person {
    
    private final String name;
    private final int age;
    private int mixinMerged_phoneNumber;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public void mixinMerged_sayHello() {
        System.out.println("Hello Fabric!");
    }
    
}
```

As we can see, the members of the mixin class were merged with the members of the `Person` class. In short, **a mixin class declares changes and additions to its target class at runtime**.

## Injecting code {#injecting-code}

Another thing that mixin classes allow us to do, is declare methods that will have calls to them placed in one or multiple methods of the target class.

Consider our previous `Person` class. Let's say that we want to log to the console every time the `age` field is accessed via its getter method, perhaps with a method like this one:

```java
public void logAgeAccess() {
    System.out.println("The age field of person " + this + " has been accessed!");
}
```

We would like some way of telling the mixin class to place a call to this method, in a place like this one.

```java
    public int getAge() {
        // Inject  my logAgeAccess() method here
        return this.age;
    }
```

We would first add the method to the `Person` class by just adding it to the `PersonMixin` mixin class. But now, how do we make it be called in `getAge()`?

Let's say that we have an annotation, named `@InjectCallToMethod`, that takes as arguments the method that you want to inject a call into, and where in the method to place that call in. This annotation will be used on methods declared in the mixin class.
Then, we can annotate our `logAgeAccess` method with our annotation in the mixin class.


```java

@Mixin(Person.class)
public abstract class PersonMixin {
    
    private int phoneNumber;
    
    public void sayHello() {
        System.out.println("Hello Fabric!");
    }
    
    @InjectCallToMethod(method = "getAge", at = @At("HEAD"))
    public void logAgeAccess() {
        System.out.println("The age field of person " + this + " has been accessed!");
    }
    
}

```

The `at` parameter here takes in an `@At` annotation as an argument, which allows us to specify with detail where we want to inject our method into. Later in this guide we will explore how to use this annotation in more detail.

Now, the `Person` class will look like this at runtime:

```java

public class Person {
    
    private final String name;
    private final int age;
    private int mixinMerged_phoneNumber;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        mixinMerged_logAgeAccess(); // <- Injected call to mixinMerged_logAccess
        return this.age;
    }
    
    public void mixinMerged_sayHello() {
        System.out.println("Hello Fabric!");
    }

    @InjectCallToMethod(method = "getAge", at = @At("HEAD"))
    public void mixinMerged_logAgeAccess() {
        System.out.println("The age field of a person has been accessed!");
    }
    
}
```

These types of annotations, that allow you to place calls to methods inside already existing methods of some target class, are called **injector annotations**, or just **injectors**, and they are the main tool offered by Mixin to help you modify Minecraft's source code.
There are many different types of annotations, many of them can do more than inject simple calls to method, and they each have their purpose and use cases. Later we will learn how to choose the right one for the goal and how to use them appropriately.

## Wait, what do you mean by "at runtime"? {#wait-what-do-you-mean-by-"at-runtime"}

It's important to emphasize the fact that the effects a mixin class has on its target class only happen **at runtime**.
**At runtime** refers to the time when the program or game is actually running, not before. This is opposed to **compile time**, that refers to stuff that happens when the program is compiled, and allows the IDE to be aware of those effects before the program is ran.

Essentially, when the game starts, a couple of things happen:

- The contents of a Mixin class are scanned, and its contents are merged with the target class.
- The target class is now transformed, and when class loaded, it will contain the elements declared from its corresponding mixin class.
- The mixin class ceases to exist. Any references to it will cause an exception.

As such, you should not think of mixin classes as regular Java classes that you can instantiate and reference. Mixin classes only serve the purpose of declaring changes and injections into its target class.
Because of this, **you should never reference a mixin class directly from anywhere in your code**, as the mixin class will not exist whenever you run the game.
The fact that mixin classes are only taken into account at runtime presents certain challenges. Let's explore one of the most common ones.

## Accessing members of a target class {#accessing-members-of-a-target-class}

Suppose that in our `logAgeAccess()` injected method, we would also like to show the age of the person.

We can attempt to modify our injected method like so:

```java
    @InjectCallToMethod(method = "getAge", at = @At("HEAD"))
    public void mixinMerged_logAgeAccess() {
        System.out.println("The age field with value " + this.age + "of a person has been accessed!");
    }
```

But there's a problem. Remember that before **runtime**, the mixin class just looks like a regular class to the compiler.
When we type `this.age`, you are trying to access the `age` field of the `Person` class, which is the target class, but it's not the actual class that you are writing it into. The compiler thinks you are trying to access the `age` field of the `PersonMixin` class.
If `age` is a field that doesn't exist in the mixin class, then attempting to run the game will result in a compile time error.

But here's the thing, we *know* that the `age` field will be available to the injected method when the game runs, as the method will be merged with the `Person` class. If only there was a way to tell the compiler that `age` will exist and to let us access it.

Let's consider an annotation that does that, called `@ThisWillExist`. What we do now is that we add the `age` field to our mixin class like any other regular field, but we annotate it with `@ThisWillExist` to let the compiler know that this field will refer to the `age` field of the mixin's target class. 
Then, we can modify our mixin class like so:

```java

@Mixin(Person.class)
public abstract class PersonMixin {
    
    @ThisWillExist
    private int age;
    private int phoneNumber;
    
    public void sayHello() {
        System.out.println("Hello Fabric!");
    }
    
    @InjectCallToMethod(method = "getAge", at = @At("HEAD"))
    public void logAgeAccess() {
        System.out.println("The age field with value " + this.age + "of a person has been accessed!");
    }
    
}

```

Now, we will not get a compiler error when we try to run the game. At runtime, `this.age` will refer to the `age` field of the `Person` class and, if such a field does in fact exist in the target class, then we will not get a runtime error either.
What we just did, the process of accessing a member of the target class by marking it as "will exist" in our mixin class, is called **Shadowing**. In reality, the proper annotation is `@Shadow`. In the case of methods, the method declaration can be left with an empty body, if the mixin class is non-abstract, or abstract of the mixin class is abstract.
On the topic of abstract mixin classes, let's finally discuss why mixin classes should be abstract.

## Abstract mixin classes {#abstract-mixin-classes}

There are a few advantages of making your mixin classes abstract. Apart from making semantic sense (a mixin class should never be instantiated directly), it allows you to access the `this` instance.

In our `logAgeAccess` we reference the `this` instance directly and it works without having to cast it explicitly to the target class. If the mixin class was non-abstract, the method would look like this.

```java
    @InjectCallToMethod(method = "getAge", at = @At("HEAD"))
    public void mixinMerged_logAgeAccess() {
        System.out.println("The age field with value " + ((Person) (Object) this).age + "of a person has been accessed!");
    }
```

Note that, for passing the `this` instance to methods, you still need to cast it, first to `Object`, then to the target class, explicitly.

# Wrapping up {#wrapping-up}

We have now learned the key concepts needed to understand mixins, which will help us understand the different tools offered by mixins, and certain "tricks" you can accomplish with them:

- A **Mixin class** is a Java class that declares changes and additions to a target class.
- The members of a mixin class are merged **at runtime** with the target class. Before this, certain "hacks" are needed to circumnavigate the compiler.
- A Mixin class supports injecting calls to merged methods inside other methods of the target class.
- **At runtime** the mixin class no longer exists. Thus, it should never be referenced directly from anywhere in your mod.

We are now ready to get started with creating our first mixin in our Fabric mod!