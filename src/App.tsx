import React from 'react'
import Landing from './components/Landing'
import Gallery from './components/Gallery'
import Highlights from './components/Highlights'
import SeasonTable from './components/SeasonTable'
import { Navigate, Route, Routes } from 'react-router-dom'

// Home page combines hero, highlights & table
const HomePage: React.FC = () => (
    <>
        <Landing />
        <div className="split">
            <Highlights />
            <SeasonTable />
        </div>
    </>
)

// Gallery page
const GalleryPage: React.FC = () => (
    <>
        <h2 className="accent">View Gallery</h2>
        <Gallery />
    </>
)

const App: React.FC = () => (
    <>
        <div style={{ textAlign: 'center', margin: '2rem 0' }}>
            <h2 className="text-accent text-3xl font-bold">🏒 BigV Webpage 🏒</h2>
        </div>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/gallery" element={<GalleryPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    </>
)

export default App
