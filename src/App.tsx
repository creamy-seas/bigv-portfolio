import React from 'react'
import Landing from './components/Landing'
import Gallery from './components/Gallery'
import Highlights from './components/Highlights'
import SeasonTable from './components/SeasonTable'
import { Link, Navigate, Route, Routes } from 'react-router-dom'
import GameGraph from './components/GameGraph'

// Home page combines hero, highlights & table
const HomePage: React.FC = () => (
    <div className="container mx-auto px-4 space-y-8">
        <Landing />
        <div className="text-center">
            <Link
                to="/gallery"
                className="
          text-myflame text-3xl font-bold
          underline decoration-myflame underline-offset-4
          hover:text-myflame/80
        "
            >
                View Gallery
            </Link>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <Highlights />
            <SeasonTable />
        </div>
        <GameGraph/>
    </div>
)

// Gallery page
const GalleryPage: React.FC = () => (
    <>
        <Gallery />
    </>
)

const App: React.FC = () => (
    <>
        <div style={{ textAlign: 'center', margin: '2rem 0' }}>
            <Link
                to="/"
                className="
          text-myflame text-3xl font-bold
          decoration-myflame underline-offset-4
          hover:text-myflame/80
        "
            >
                🏒 BigV Webpage 🏒
            </Link>
        </div>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/gallery" element={<GalleryPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    </>
)

export default App
