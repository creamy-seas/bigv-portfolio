import React from 'react'
import Landing from './components/Landing'
import Gallery from './components/Gallery'
import Highlights from './components/Highlights'
import SeasonTable from './components/SeasonTable'
import { Link, Navigate, Route, Routes } from 'react-router-dom'

// Home page combines hero, highlights & table
const HomePage: React.FC = () => (
    <>
        <div style={{ textAlign: 'center', margin: '2rem 0' }}>
            <h1 className="accent">BigV Webpage 🏒</h1>
        </div>
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
        <div style={{ textAlign: 'center', margin: '2rem 0' }}>
            <h1 className="accent">BigV Webpage 🏒</h1>
        </div>
        <h2 className="accent">View Gallery</h2>
        <Gallery />
    </>
)

const App: React.FC = () => (
    <>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/gallery" element={<GalleryPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    </>
)

export default App
