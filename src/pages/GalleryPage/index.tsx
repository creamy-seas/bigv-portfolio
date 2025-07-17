import React, { useEffect, useMemo, useState } from "react";
import { loadGallery, type GalleryEntry } from "../../utils/loadGallery";

const navArrow =
  "absolute top-1/2 -translate-y-1/2 p-2 bg-white/90 hover:bg-white focus:ring-2 focus:ring-myflame rounded-full text-2xl text-bg";

function driveUrls(id: string) {
  return {
    thumbnail: `https://drive.google.com/thumbnail?id=${id}`,
    iframe: `https://drive.google.com/file/d/${id}/preview`,
  };
}

/**
 * Adding a search param e.g. `?season=2024-2025` will automatically expand the relevant season
 */
const GalleryPage: React.FC = () => {
  const [data, setData] = useState<Record<string, GalleryEntry[]>>({});
  useEffect(() => {
    loadGallery().then((rows) => {
      const bySeason: Record<string, GalleryEntry[]> = {};

      for (const row of rows) {
        (bySeason[row.season] ??= []).push(row);
      }
      // newest-first inside each season
      Object.values(bySeason).forEach((ary) =>
        ary.sort((a, b) => b.date.getTime() - a.date.getTime()),
      );
      setData(bySeason);
    });
  }, []);

  const params = useMemo(() => new URLSearchParams(window.location.search), []);
  const seasonFilter = params.get("season");

  // Tracking of expansion states
  const [open, setOpen] = useState<Record<string, boolean>>({});
  useEffect(() => {
    if (seasonFilter) setOpen((o) => ({ ...o, [seasonFilter]: true }));
  }, [seasonFilter]);

  // Tracking of what is in view
  const [modal, setModal] = useState<{ season: string; index: number } | null>(
    null,
  );
  const closeModal = () => setModal(null);
  const showPrev = () =>
    setModal((m) =>
      m
        ? {
            ...m,
            index:
              (m.index + data[m.season].length - 1) % data[m.season].length,
          }
        : m,
    );
  const showNext = () =>
    setModal((m) =>
      m ? { ...m, index: (m.index + 1) % data[m.season].length } : m,
    );

  return (
    <>
      <section className="container select-none">
        {Object.entries(data).map(([season, items]) => (
          <div
            key={season}
            className={`collapse collapse-arrow ${open[season] ? "collapse-open" : ""}`}
            onClick={() => setOpen({ [season]: !open[season] })}
          >
            <div className="collapse-title text-xl font-semibold bg-myflame/80 text-bg select-none">
              {season}
            </div>

            <div className="collapse-content p-2">
              <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
                {items.map((item, idx) => {
                  const { thumbnail } = driveUrls(item.id);
                  return (
                    <div
                      key={item.id}
                      className="cursor-pointer"
                      onClick={(e) => {
                        // Keep collapse open and open the modal
                        e.stopPropagation();
                        setModal({ season, index: idx });
                      }}
                    >
                      <img
                        src={
                          item.type === "image" ? thumbnail : "play-icon.svg"
                        }
                        alt={item.description}
                        className="w-full h-32 object-cover rounded-lg"
                      />
                      <p className="text-sm text-center mt-2">
                        {item.description}
                      </p>
                      <p className="text-xs text-center text-gray-400">
                        {item.date.toLocaleDateString()}
                      </p>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        ))}
      </section>

      {modal && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center bg-black/75 p-4"
          onClick={closeModal}
        >
          <div
            className="relative bg-bg p-6 rounded-lg w-full lg:max-w-[70vw] overflow-auto"
            onClick={(e) => e.stopPropagation()}
          >
            <button onClick={showPrev} className={`${navArrow} left-4`}>
              ‹
            </button>
            <button onClick={showNext} className={`${navArrow} right-4`}>
              ›
            </button>

            <div className="w-full aspect-video">
              <iframe
                src={driveUrls(data[modal.season][modal.index].id).iframe}
                className="w-full h-full"
                allow="autoplay"
                allowFullScreen
                title={data[modal.season][modal.index].description}
              />
            </div>

            <h3 className="text-xl font-semibold text-myflame mt-4">
              {data[modal.season][modal.index].description}
            </h3>
            <p className="text-sm text-gray-400">
              {data[modal.season][modal.index].date.toLocaleDateString()}
            </p>
          </div>
        </div>
      )}
    </>
  );
};

export default GalleryPage;
