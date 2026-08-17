{
  description = "Minecraft mod development";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs {
        inherit system;
      };
    in {
      devShells.${system}.default = pkgs.mkShell {
        packages = with pkgs; [
          jdk25
          gradle
          libglvnd
          glfw
          openal
        ];

        LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath [
          pkgs.libglvnd
          pkgs.glfw
          pkgs.openal
        ] + ":/run/opengl-driver/lib";
      };
    };
}
