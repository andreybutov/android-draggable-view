## Android Draggable View

An implementation of a draggable Android View that can be modified as it moves.
### Overview
The standard approach to implementing a draggable/movable **View** in Android is to rely on **DragShadowBuilder**. For many situations, this would be the easiest approach. However, in some cases, where, for example, you need to modify the view as it is being dragged, DragShadowBuilder won't do, as it's just not flexible enough.

This is an example of an alternate implementation of a simulated draggable View, without the dependency on DragShadowBuilder. With this approach you have full access to the view being dragged (actually, the cached **Bitmap** image of the view), and, as such, the view can be modified while moving.

### Implementation Notes

1. To reduce flicker and improve animation performance, this implementation simulates the movement of a View by moving a cached Bitmap rendition of the View, instead of the View itself. This technique results in a much smoother effect.

2. In this example project, the transparency of the view is modified as the View is dragged around the screen.

### License

The MIT License (MIT)

Copyright (c) 2014-2015, Andrey Butov. All Rights Reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
