# Robot Improvement Roadmap

## Overview
This document categorizes planned changes to make the project robust, testable, and easy to run as a published Docker image.

---

## Refactoring code (required)
- Add `server/config.js` to centralize environment parsing and validation.
- Export Express app from `server/index.js` for Supertest integration.
- Move Java invocation logic from `routes/process.js` into `server/lib/jarExecutor.js` or replace with an HTTP client to `java_api`.
- Normalize path handling in `server/repo/video.js` (use path.resolve with VIDEO_DIR fallback).
- Make `utils/jobManager.js` deterministic and testable (no hidden global state).

Tasks:
- Create `server/config.js` and replace inline process.env reads.
- Refactor `routes/process.js` to call executor module.

---

## Adding tests (required)
- Unit tests:
  - `test/video.test.js` — list, exists, error cases (mock fs).
  - `test/jobManager.test.js` — create/complete/fail lifecycle.
- Integration tests (Supertest):
  - `test/api.integration.test.js` — health, list videos, job create (mock jar executor).
- End-to-end:
  - Compose-based E2E that runs `docker-compose up --build`, posts a job, polls status, and checks results folder.
- CI:
  - Add `.github/workflows/test.yml` to run `npm test` and optionally build images.

---

## Improving error handling (required)
- Startup validation in `server/index.js`:
  - Fail with clear message if VIDEO_DIR or RESULTS_DIR are missing or unreadable.
- Defensive programming:
  - Replace `fs.readdir(dir)` with guarded async helper that returns a structured error object.
  - Validate `JAR_PATH` and check `fs.existsSync(JAR_PATH)` before spawn.
  - Wrap child process spawning: attach `error` and `exit` handlers; update job state on failure.
- Healthchecks:
  - Add `/api/health` and Docker `HEALTHCHECK` to Dockerfile that hits `/api/health`.
- Unhandled Child process error in `videoServices`
