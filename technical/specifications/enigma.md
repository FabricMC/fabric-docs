---
title: Enigma Specification
description: The specification for the Enigma mappings file format.
---

# Enigma Mapping Format

The Enigma mapping format consists of a list of hierarchical sections.
Every line starts a new section, whether it continues an existing
section is determined by the indentation level. A section's parent is
always the closest preceding section indented once less than itself.
Accordingly, a section ends just before the next line with the same or a
lesser indentation level.

The child-to-parent relationships form the paths to uniquely identify
any element globally. For example, all method and field sections that
are children of a class section represent members of the represented
class. Sections need to be unique within their level. For example a
specific class may only be recorded once, a comment can't be redefined
or the same parameter listed twice.

Supported elements are classes, fields, methods, parameters and
comments. If you need support for variables, namespaces, arbitrary
top-level metadata and more resistance against obfuscated names, [Tiny
v2](./tiny/v2.md) is recommended instead.

**Example:**

```enigma:no-line-numbers
CLASS a pkg/SomeClass
  FIELD a	someField [I
  METHOD a someMethod (III)V
    ARG 1 amount
    # This is a file comment. It can be put basically everywhere, except in the same line as a COMMENT.
CLASS b pkg/xy/AnotherClass ACC:PUBLIC
  COMMENT This is a
  COMMENT multiline comment. You can use <b>HTML tags</b> if you like, or leave
  COMMENT
  COMMENT empty lines.
  METHOD a anotherMethod (Ljava/lang/String;)I
  CLASS c InnerClass
    COMMENT This class's obfuscated fully qualified name is {@code b$c}.
```

## Grammar

```bnf:no-line-numbers
<file>                          ::= <content>
<content>                       ::= '' | <class-section> <content>

<class-section>                 ::= <class-section-indentation> 'CLASS' <space> <class-name-a> <class-name-b> <formatted-access-modifier> <eol> <class-sub-sections>
<class-section-indentation>     ::= '' | <tab> <class-section-indentation>
<class-name-a>                  ::= <class-name>
<class-name-b>                  ::= <optional-class-name>
<optional-class-name>           ::= <empty-mapping> | <space> <class-name>
<class-name>                    ::= <spaceless-safe-string>
<class-sub-sections>            ::= '' | <class-comment-section> <class-sub-sections> | <field-section> <class-sub-sections> | <method-section> <class-sub-sections> | <class-section> <class-sub-sections>

<field-section>                 ::= <class-section-indentation> <tab> 'FIELD' <space> <field-name-a> <field-name-b> <formatted-access-modifier> <field-desc-a> <eol> <field-sub-sections>
<field-name-a>                  ::= <field-name>
<field-name-b>                  ::= <optional-field-name>
<optional-field-name>           ::= <empty-mapping> | <space> <field-name>
<field-name>                    ::= <spaceless-safe-string>
<field-desc-a>                  ::= <field-desc>
<field-desc>                    ::= <spaceless-safe-string>
<field-sub-sections>            ::= '' | <member-comment-section> <field-sub-sections>

<method-section>                ::= <class-section-indentation> <tab> 'METHOD' <space> <method-name-a> <method-name-b> <formatted-access-modifier> <method-desc-a> <eol> <method-sub-sections>
<method-name-a>                 ::= <method-name>
<method-name-b>                 ::= <optional-method-name>
<optional-method-name>          ::= <empty-mapping> | <space> <method-name>
<method-name>                   ::= <spaceless-safe-string>
<method-desc-a>                 ::= <method-desc>
<method-desc>                   ::= <spaceless-safe-string>
<method-sub-sections>           ::= '' | <member-comment-section> <method-sub-sections> | <method-parameter-section> <method-sub-sections>

<method-parameter-section>      ::= <class-section-indentation> <tab> <tab> 'ARG' <space> <lv-index> <space> <parameter-name-b> <eol> <method-parameter-sub-sections>
<lv-index>                      ::= <non-negative-int>
<parameter-name-b>              ::= <optional-parameter-name>
<optional-parameter-name>       ::= <empty-mapping> | <space> <parameter-name>
<parameter-name>                ::= <spaceless-safe-string>
<method-parameter-sub-sections> ::= '' | <parameter-comment-section> <method-parameter-sub-sections>

<empty-mapping>                 ::= '' | <space> '-'
<formatted-access-modifier>     ::= '' | <space> 'ACC:' <access-modifier>
<access-modifier>               ::= 'UNCHANGED' | 'PUBLIC' | 'PROTECTED' | 'PRIVATE'

<class-comment-section>         ::= '' | <class-section-indentation> <tab> <indented-comment-section> <eol> <class-comment-section>
<member-comment-section>        ::= '' | <class-section-indentation> <tab> <tab> <tab> <indented-comment-section> <eol> <member-comment-section>
<parameter-comment-section>     ::= '' | <class-section-indentation> <tab> <tab> <tab> <tab> <indented-comment-section> <eol> <parameter-comment-section>
<indented-comment-line>         ::= 'COMMENT' <comment-content-line>
<comment-content-line>          ::= <safe-string>
```

### Notes

-   `<tab>` is `\t`.
-   `<space>` is the space character (`U+0020`).
-   `<eol>` is `\n` or `\r\n`.
-   `<safe-string>` is a non-empty string that must not contain:
    -   `\`,
    -   `\n`,
    -   `\r`,
    -   `\t` or
    -   `\0`.
-   `<spaceless-safe-string>` is the same as `<safe-string>`, but in
    addition mustn't contain `<space>` as well.
-   Each line of an unprocessed comment string gets its own
    `<comment-content-line>`.
-   `<non-negative-int>` is any integer from 0 to 2147483647 (2^31-1)
    inclusive, represented as per `java.lang.Integer.toString()`.
-   `<class-name>`, is the binary name of a class as specified in JVMS
    SE 8 §4.2.1. Inner classes get their own `<class-section>` within
    their outer class's `<class-section>`. The outer names should be
    omitted then, though older versions of the format didn't do this.
-   `<class-section-indentation>` is `''` for top-level
    `<class-section>`s. Inner classes' `<class-section-indentation>`
    increases by one `<tab>` per nest level. E.g. a doubly-nested inner
    class `Outer$Inner$InnerInner` must have a
    `<class-section-indentation>` of one `<tab>` for `Inner` and two
    `<tab>`s for `InnerInner`.
-   `<field-name>`/`<method-name>`/`<parameter-name>` is the unqualified
    name of a field/method/parameter as specified in JVMS SE 8 §4.2.2.
-   `<field-desc>` is a field descriptor as specified in JVMS SE 8
    §4.3.2.
-   `<method-desc>` is a method descriptor as specified in JVMS SE 8
    §4.3.3.
-   `<lv-index>` refers to the local variable array index of the frames
    having the variable, see "index" in JVMS SE 8 §4.7.13.

## Miscellaneous Notes

-   The encoding for the entire file is UTF-8. Escape sequences are
    limited to the types, locations and conditions mentioned above.
-   Indenting uses tab characters exclusively, one tab character equals
    one level. The amount of leading tab characters is at most 1 more
    than in the preceding line.
-   Sections with unknown types should be skipped without generating an
    error.
-   Sections representing the same element must not be repeated, e.g.
    there can be only one top-level section for a specific class or one
    class-level section for a specific member.
-   Mappings without any (useful) names should be omitted.
-   Sections without any (useful) mappings or sub-sections should be
    omitted.
-   Comments should be without their enclosing syntax elements,
    indentation or decoration. For example, the comment
           /**
            * A comment
            * on two lines.
            */

    (note the indentation) should be recorded as
        COMMENT A comment
        COMMENT on two lines.

