import express from 'express';
import cors from 'cors';
import videoRouter from './routes/videoRoutes.js';

const app = express();
const PORT = 3000;

app.use(cors());

app.use(express.static('public'));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use('/', videoRouter);

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});