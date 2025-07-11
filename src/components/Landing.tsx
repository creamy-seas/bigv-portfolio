import React from 'react'
import { AGE, TEAM, CURRENT_SEASON } from '../config'

const Landing: React.FC = () => {
  const games = 18;
  const goals = 4;
    const hours = 368;
    return (
        <section className="flex md:flex-row items-center bg-bg p-6 rounded-lg shadow-lg">
            <div className="flex-1 space-y-2 md:space-y-4">
                <h2 className="text-2xl font-bold">Viktor Antonov</h2>
                <dl className="grid grid-cols-2 gap-y-2">
                    {[
                        ['Age', AGE],
                        ['Team', TEAM],
                        ['Season', CURRENT_SEASON],
                        ['Career Games', games],
                        ['Career Goals', goals],
                        ['Career Hours', hours],
                    ].map(([label, val]) => (
                        <React.Fragment key={label}>
                            <dt className="font-semibold text-accent">{label}:</dt>
                            <dd>{val}</dd>
                        </React.Fragment>
                    ))}
                </dl>
            </div>
            <img
                src="/profile.jpeg"
                alt="Player Photo"
                className="flex-shrink-0 rounded-[5%] max-w-[360px] h-[360px]"
            />
        </section>
    )
}

export default Landing
