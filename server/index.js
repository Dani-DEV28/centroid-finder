//Daniel - AI generate

import express from "express";
import dotenv from "dotenv";
import path from "path";

import apiRoutes from "./routes/api.js";
import thumbnailRoutes from "./routes/thumbnail.js";
import processRoutes from "./routes/process.js";

dotenv.config({ path: path.resolve("../.env") });

const app = express();
const PORT = process.env.PORT || 3000;

// Read either env name (backwards-compatible) and guard undefined
const videosDir = process.env.VIDEOS_DIR || process.env.VIDEO_DIR || "";
const resultsDir = process.env.RESULTS_DIR || process.env.RESULTS_DIR || "";

if (videosDir) {
  app.use("/videos", express.static(path.resolve(videosDir)));
} else {
  console.warn("VIDEOS_DIR / VIDEO_DIR not set — /videos static route not mounted");
}

if (resultsDir) {
  app.use("/results", express.static(path.resolve(resultsDir)));
} else {
  console.warn("RESULTS_DIR not set — /results static route not mounted");
}

// Mount API routes
app.use("/api", apiRoutes);          // /api/videos
app.use("/thumbnail", thumbnailRoutes); // /thumbnail/:filename
app.use("/process", processRoutes);     // /process/:filename and /process/:jobId/status

// Basic health check route (optional)
app.get("/", (req, res) => {
  res.send("Salamander Video Processing Server is running!");
});

app.listen(PORT, () => console.log(`Server running at http://localhost:${PORT}`));