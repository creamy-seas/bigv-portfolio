import React from 'react'
import {age} from "../config"

interface LandingProps {
  name?: string
  games?: number
  goals?: number
  hours?: number
}

const Landing: React.FC<LandingProps> = ({
  games = 18,
  goals = 4,
  hours = 204
}) => (
    <section className="hero">
        <div className="stats">
            <p>Age: {age}</p>
            <p>Games: {games}</p>
            <p>Goals: {goals}</p>
            <p>Ice Hours: {hours}</p>
        </div>
        <img src="/profile.jpeg" alt="Player Photo" />
    </section>
)

export default Landing
