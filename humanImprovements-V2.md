# Improvements
- ## Refactoring Code
    - What improvements can you make to the design/architecture of your code?
        - Simplify the API design, to make it more readable and easier to troubleshoot
        - Enable the Java backend to recongnize Image is passed to the backend
        - Docker: Currently was built with two file, being compose. But their issues with have the backend having it own port – ✅ finalize fix in commit: [`2eda90e`](https://github.com/Dani-DEV28/centroid-finder/commit/2eda90e23dba7fd4cdeff40c26d9fc5ce92fb3bc)
        - `Processor/`, as the current String argument pass is making it difficult to test
        - `getVideos` and `getResult` merge into a single function called `getList`  – ✅ updated: [`2eda90e`](https://github.com/Dani-DEV28/centroid-finder/commit/1b07b390be1853bf163b1bf949b4860d7491c30c)
    - Where would additional Java interface be apporiate?
        - When looking to process just the thumbnail, sending it straingt to the `ImageSummaryApp`, as it would skip `VideoProcessor` and `frameExt`
    - How can you make things simpler, more-usable, and easier to maintain?
        - Add Documentation
        - Removing redundancy in the `ImagesummaryApp`, since the `VideoProcessor` is already handling the string args parsing

- ## Adding Tests
    - What portions of youer code are untested / only lightly testd?
        - API need to have the test automating, as it only been lightly tested through the browser and `"http://localhost:3000/process/salamander.jpg?targetColor=ff0000&threshold=120"` – For the POST
        - `VideoProcessor` and `frameExt` have not had automated testing with J-Unit Test, only been lightly tested with manual entries.
    - Where would be the highest priority places to add new tests?
        - Definately the API routes, as they are the most brittle
        

- ## Improving Error Handling
    - What parts of your code are brittle? \
        - `frameExt` if image file is passed instead of a video – ✅ [`8473217`](https://github.com/Dani-DEV28/centroid-finder/commit/8473217b1d940cf044971b504f6ec6acba201cb3)
        - 
    - How can you better be resolving/logging/surfacing errors?
        - `videoServices.js` add more error check points, with clear descripction of the issues

- ## Writing Documentation
    - What portions of your code are missing Javadoc/JSdoc for the methods/classes?
        - `frameExt.java` – ✅ Added in [`1839117`](https://github.com/Dani-DEV28/centroid-finder/commit/1839117625c5973f51ca4d71f5fa74b6018bf5b0)
        - `VideoProcessor.java`: Redundant CSV check in line 31-44
    - What documentation could be made clearer or improved?
        - `frameExt.java`
        - `VideoProcessor.java`
        - files under `server/` directory
    - Are there sections of dead code that are commented out?
        - `EuclideanColorDistance.java`
        - `videoServices.js`
        - `binarizerImg` func in the `server`
        - `getImage` func in the `server`
        - `frameExt` section to detect img <- No longer needed
