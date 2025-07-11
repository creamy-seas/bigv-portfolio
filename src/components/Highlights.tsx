import React from 'react'

const highlights = [
  'First goal: Feb 2025',
  'Captain in Spring Camp',
  'Most assists in team practice'
]

const Highlights: React.FC = () => (
    <section>
        <h3>🎉 Career Highlights</h3>
        <ul>
            {highlights.map((h, i) => <li key={i}>{h}</li>)}
        </ul>
    </section>
)

export default Highlights
