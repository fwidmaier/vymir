# vymir - вимір (Ukrainian for 'dimension')

<img src="doc/cover.png" alt="drawing"/>

A simple renderer for .obj files in pure Java.

For rendering we use a Z-buffer and backface culling:

<img src="doc/test.gif" alt="teapot.obj" width="300"/> <img src="doc/cow.gif" alt="cow.gif" width="300"/> <img src="doc/culling.png" alt="teapot.obj (wireframe)" width="300"/> <img src="doc/apple.png" alt="apple.obj" width="300"/> <img src="doc/knot.gif" alt="drawing" width="300"/> <img src="doc/halloween.gif" alt="drawing" width="300"/>

<img src="doc/dog.png" alt="drawing"/>

Rendering the [animation](doc/test.gif) shown above took about 10ms per frame - at least on my machine...

In [halloween.gif](doc/halloween.gif), [this .obj](https://www.turbosquid.com/3d-models/free-halloween-pumpkin-3d-model/961113) was used.
In [dog.png](doc/dog.png) and [dog.mp4](doc/dog.mp4), [this.obj](https://www.turbosquid.com/3d-models/dachshund-3d-model-1193676) was used.
