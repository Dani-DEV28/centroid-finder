# Video4j vs JCodec

## Video4j

### ✅ Pros
- Large, useful toolset (e.g., specify a frame, get total frames of a video, get dimensions of a video, etc.)
- Can convert a video frame to a `BufferedImage`
- Can get the FPS (frames per second) of a video

### ❌ Cons
- Only supported on **Linux**
- Uses the **OpenCV** library
- The **Libavcodec** library used by OpenCV limits supported video formats


## JCodec

### ✅ Pros
- Setup is simpler compared to other libraries like OpenCV and its derivatives
- For the project we are working on, it does exactly what we need — **frame extraction**

### ❌ Cons
- Limited to the **H.264** codec  
  (However, this isn’t much of a con since we are working with MP4 files encoded in H.264)
