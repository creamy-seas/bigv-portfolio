import React from 'react'

interface Season {
  id: string
  title: string
  folder: string
  items: { src: string; caption: string }[]
}

const seasons: Season[] = [
  {
    id: '2024',
    title: '2024 Season',
    folder: '/assets/2024/',
    items: [
      { src: '/assets/2024/1.jpg', caption: 'Game 1 Goal' },
      // …more items
    ]
  },
  // add other seasons here
]

const Gallery: React.FC = () => (
    <>
        {seasons.map(s => (
            <section key={s.id}>
                <h3 className="accent">{s.title}</h3>
                <div className="gallery-grid">
                    {s.items.map((it, i) => (
                        <article className="gallery-item" key={i}>
                            <img src={it.src} alt={it.caption} />
                            <p>{it.caption}</p>
                        </article>
                    ))}
                </div>
            </section>
        ))}
    </>
)

export default Gallery
