import React, { useEffect, useState } from 'react'
import Papa from 'papaparse'

interface SeasonData {
  Season: string
  Games: string
  Goals: string
  Hours: string
}

const SeasonTable: React.FC = () => {
  const [data, setData] = useState<SeasonData[]>([])

  useEffect(() => {
    fetch('/src/data/seasons.csv')
      .then(r => r.text())
      .then(txt => {
        const result = Papa.parse<SeasonData>(txt, { header: true })
        setData(result.data.filter(d => d.Season))
      })
  }, [])

  return (
    <section>
      <h3>📊 Season Stats</h3>
      <table>
        <thead>
          <tr>
            <th>Season</th><th>Games</th><th>Goals</th><th>Hours</th>
          </tr>
        </thead>
        <tbody>
          {data.map((r, i) => (
            <tr key={i}>
              <td>{r.Season}</td>
              <td>{r.Games}</td>
              <td>{r.Goals}</td>
              <td>{r.Hours}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  )
}

export default SeasonTable
