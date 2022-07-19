# PaletteSwatcher
 
A small program used for the adjustment of a image or multiple images to follow a specific palette of colors.\
This program is able to run a test-output on one or more images with one or more palettes into a each of these combinations of outputs.

WARNING: Running the non-testing function will REPLACE existing images, so backup is recommended.
After a palette has been chosen, you can input a path, where all files in the path(including subfolders) will be targetted, and converted, based on the palette.
# Before & After
![Alt text](./lul.png "LUL Before")
![Alt text](./lul_truffles_color_palette.png "LUL After")
\
This was the result from the following palette:
\
![Alt text](./truffles_palette.png "Truffles Palette")

# Usage
This program is primarily meant for game developers to "mix" up their graphical implementations, and see how different palettes feel.\
Another use is if you use third party graphical assets, they can then be converted to follow the same palette and therefore give a much better coherency all across the game.\
To use this, you have to replace the strings inside Main.java to match your folders, images & palettes. Furthermore to change from testing output, you have to uncomment the function in the main loop.\
Palettes can be downloaded in .txt from https://www.pixilart.com \
Palettes could work from other sites, but if they aren't comma delimited files with hex colors, they might not work without adjusting the code.