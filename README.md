# vymir - вимір (Ukrainian for 'dimension')

<img src="doc/cover.png" alt="drawing"/>

A simple renderer for .obj files in pure Java.

For rendering we use a Z-buffer and backface culling:

<img src="doc/test.gif" alt="teapot.obj" width="300"/> <img src="doc/cow.gif" alt="cow.gif" width="300"/> <img src="doc/culling.png" alt="teapot.obj (wireframe)" width="300"/> <img src="doc/apple.png" alt="apple.obj" width="300"/> <img src="doc/knot.png" alt="drawing" width="300"/>

Rendering the [animation](doc/test.gif) shown above took about 10ms per frame - at least on my machine...
