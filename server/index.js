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

// Serve static directories
// Makes videos accessible at /videos/VIDEO_NAME
app.use("/videos", express.static(path.resolve(process.env.VIDEO_DIR)));
// Makes processed results accessible at /results/RESULT_FILE
app.use("/results", express.static(path.resolve(process.env.RESULTS_DIR)));

// Mount API routes
app.use("/api", apiRoutes);          // /api/videos
app.use("/thumbnail", thumbnailRoutes); // /thumbnail/:filename
app.use("/process", processRoutes);     // /process/:filename and /process/:jobId/status

// Basic health check route (optional)
app.get("/", (req, res) => {
  res.send("Salamander Video Processing Server is running!");
});

app.listen(PORT, () => console.log(`Server running at http://localhost:${PORT}`));