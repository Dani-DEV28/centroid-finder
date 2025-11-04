import express from 'express';
//import { spawn } from 'child_process';
import apiRouter from "./routes/api.js";
import tumbRouter from "./routes/api.js";
//import processRouter from "./routes/api.js";

const app = express()

app.use("/", apiRouter);
app.use("/", tumbRouter);
//app.use("/", processRouter);

app.listen(3000, console.log(`Server running at http://localhost:${PORT}`));