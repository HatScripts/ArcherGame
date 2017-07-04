# ArcherGame <img src="https://cdn.rawgit.com/HatScripts/ArcherGame/master/logo.svg" alt="ArcherGame logo" height="48" align="right">

ArcherGame* is a top-down 2D action game**.

<sup><a name="footnote1">*</a> "ArcherGame" is just a working title/placeholder until I decide on an official name.</sup>

<sup><a name="footnote2">**</a> This is very much a work in progress, and there's no real gameplay to it yet.</sup>

## Download

See [releases](https://github.com/HatScripts/ArcherGame/releases).

## Controls

Key(s)                       | Description
-----------------------------|------------
<kbd>W</kbd> or <kbd>↑</kbd> | Walk up
<kbd>S</kbd> or <kbd>↓</kbd> | Walk down
<kbd>A</kbd> or <kbd>←</kbd> | Walk left
<kbd>D</kbd> or <kbd>→</kbd> | Walk right
<kbd>Ctrl</kbd>+<kbd>D</kbd> | Toggle debug mode

##### Debug mode

Key(s)                               | Description
-------------------------------------|------------
Left-click and drag                  | Create a square object
<kbd>Ctrl</kbd>+Left-click and drag  | Create a circle object
<kbd>Shift</kbd>+Left-click and drag | Create a triangle object
Right-click and drag                 | Delete objects

## Todo

- [ ] MouseInput - Find a pure JavaFX (non-AWT) way to get the mouse location when it's outside the window
- [ ] Circle - Make collision box circular
- [ ] Triangle - Make collision box triangular
- [x] Debug - Fix runtime showing as `10:MM:SS` instead of `HH:MM:SS`
- [ ] Debug - Make FPS display smoother
- [ ] Player - Add eyelids; blinking, squinting, etc.
- [ ] Player - Make pupils follow cursor vertically as well, not just horizontally
- [ ] Player - Add arrow(s), and hand that holds them
- [ ] Player - Pull back bowstring when left mouse button held down
- [ ] Player - Fire an arrow when left mouse button released
- [ ] AbstractMobileObject - Improve collision detection

## Acknowledgements

- The current logo (a placeholder) is from [Icons8](https://icons8.com/icon/40180/archer)