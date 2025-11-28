# Improvements
- ## Refactoring Code
    - What improvements can you make to the design/architecture of your code?
        - Simplify the API design, to make it more readable and easier to troubleshoot
        - Enable the Java backend to recongnize Image is passed to the backend
    - Where would additional Java interface be apporiate?
        - When looking to process just the thumbnail, sending it straingt to the `ImageSummaryApp`, as it would skip `VideoProcessor` and `frameExt`
    - How can you make things simpler, more-usable, and easier to maintain?
        - Add Documentation
        - 

- ## Adding Tests
    - What portions of youer code are untested / only lightly testd?
        - API need to have the test automating, as it only been lightly tested through the browser and `"http://localhost:3000/process/salamander.jpg?targetColor=ff0000&threshold=120"` – For the POST
        - `VideoProcessor` and `frameExt` have not had automated testing with J-Unit Test, only been lightly tested with manual entries.
    - Where would be the highest priority places to add new tests?
        - Definately the API routes, as they are the most brittle

- ## Improving Error Handling
    - What parts of your code are brittle? \
        - `frameExt` if image file is passed instead of a video ✅
        - 
    - How can you better be resolving/logging/surfacing errors?
        - `videoServices.js` add more error check points, with clear descripction of the issues

- ## Writing Documentation
    - What portions of your code are missing Javadoc/JSdoc for the methods/classes?
        - `frameExt.java`
        - `VideoProcessor.java`
    - What documentation could be made clearer or improved?
        - `frameExt.java`
        - `VideoProcessor.java`
        - files under `server/` directory
    - Are there sections of dead code that are commented out?
        - `EuclideanColorDistance.java`
        - `videoServices.js`
