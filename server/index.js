import express from 'express';
import apiRouter from './routes/api.js';
import thumbnailRouter from './routes/thumbnail.js'; // <-- separate file for thumbnail

const app = express();
const PORT = 3000;

// Mount routers
app.use('/api', apiRouter);
app.use('/thumbnail', thumbnailRouter);

app.listen(PORT, () => console.log(`Server running at http://localhost:${PORT}`));
