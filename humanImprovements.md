# Improvements
- ## refactoring code
  - What improvements can you make to the design/architecture of your code?
    - Possibly put some code in from the router files into a exported function 
    storted in the controllers folder 
  - How can you split up large methods or classes into smaller components?
  - Are there unused files/methods that can be removed?
  - Where would additional Java interfaces be appropriate?
  - How can you make things simpler, more-usable, and easier to maintain?
  - Other refactoring improvements?

    - Add `server/config.js` to centralize environment parsing and validation.
    - Export Express app from `server/index.js` for Supertest integration.
    - Move Java invocation logic from `routes/process.js` into `server/lib/jarExecutor.js` or replace with an HTTP client to `java_api`.
    - Normalize path handling in `server/repo/video.js` (use path.resolve with VIDEO_DIR fallback).
    - Make `utils/jobManager.js` deterministic and testable (no hidden global state).

    Tasks:
    - Create `server/config.js` and replace inline process.env reads.
    - Refactor `routes/process.js` to call executor module.

- ## adding tests
  - What portions of your code are untested / only lightly tested?
    - The api routes
  - Where would be the highest priority places to add new tests?
    - For the api routes
  - Other testing improvements?

    - Unit tests:
        - `test/video.test.js` — list, exists, error cases (mock fs).
        - `test/jobManager.test.js` — create/complete/fail lifecycle.
    - Integration tests (Supertest):
        - `test/api.integration.test.js` — health, list videos, job create (mock jar executor).
    - End-to-end:
        - Compose-based E2E that runs `docker-compose up --build`, posts a job, polls status, and checks results folder.
    - CI:
        - Add `.github/workflows/test.yml` to run `npm test` and optionally build images.

- ## improving error handling
  - What parts of your code are brittle?
  - Where could you better be using exceptions?
  - Where can you better add input validation to check invalid input?
  - How can you better be resolving/logging/surfacing errors? Hint: almost any place you're using "throws Exception" or "catch(Exception e)" should likely be improved to specify the specific types of exceptions that might be thrown or caught.
  - Other error handling improvements>

    - Startup validation in `server/index.js`:
        - Fail with clear message if VIDEO_DIR or RESULTS_DIR are missing or unreadable.
    - Defensive programming:
        - Replace `fs.readdir(dir)` with guarded async helper that returns a structured error object.
        - Validate `JAR_PATH` and check `fs.existsSync(JAR_PATH)` before spawn.
        - Wrap child process spawning: attach `error` and `exit` handlers; update job state on failure.
    - Healthchecks:
        - Add `/api/health` and Docker `HEALTHCHECK` to Dockerfile that hits `/api/health`.

- ## writing documentation
  - What portions of your code are missing Javadoc/JSdoc for the methods/classes?
    - frameExt.java
    - VideoProcessor.java
  - What documentation could be made clearer or improved?
  - Are there sections of dead code that are commented out?
    - Yes, in a couple files
  - Where would be the most important places to add documentation to make your code easier to read?
    - The server files
  - Other documentation improvements?
- ## improving performance (optional)
  - What parts of your code / tests / Docker image run particularly slowly?
  - What speed improvements would most make running / maintaining your code better?
  - Other performance improvements?
- ## hardening security (optional)
  - What packages / images are out of date / have security issues?
  - Where could you have better input validation in your code to prevent malicious use?
  - Other security improvements?
- ## bug fixes (optional)
  - What bugs do you know exist?
  - What parts of the code do you think might be causing them?
  - Other bug fix improvements?
- ## other
  - Any other improvements in general you could make?