import { useState } from "react";
import playUrl from "../../assets/play.svg";

interface GalleryItem {
  src: string;
  caption: string;
  date: string;
  type: "image" | "video";
}

interface SeasonGallery {
  id: string;
  title: string;
  items: GalleryItem[];
}

// TODO: read from google drive
const GALLERIES: SeasonGallery[] = [
  {
    id: "2024-2025",
    title: "2024-2025 Season",
    items: [
      {
        src: "https://drive.google.com/file/d/1Pqldk8jvGGMYNvQb1r7DJcA_X-ggTGlq/view?usp=sharing",
        caption: "Team photo",
        date: "2024-09-01",
        type: "image",
      },
      {
        src: "https://drive.google.com/file/d/1OLARnc9-yO4bd0aoHZDmNO7276vlIgOq/view?usp=sharing",
        date: "2024-10-01",
        type: "image",
        caption: "Charging for the puck",
      },
      //{ src: "https://drive.google.com/file/d/1LL01ILGC-UoLR_AsljDCbzokDj9xEmDc/view?usp=sharing", date: "2024-10-16", type: "video", caption: "First person battle" },
      //{ src: "https://drive.google.com/file/d/1FAnjEKcGVM4h-QCeVugjKPst29aH5-tN/view?usp=sharing", date: "2024-10-20", type: "video", caption: "Getting stuck in" },
      {
        src: "https://drive.google.com/file/d/17xpCou8PS3MBLY7MgJ1EixOpyWo-VWqU/view?usp=sharing",
        date: "2024-10-23",
        type: "image",
        caption: "Mascoting for the Berkshire Bees",
      },
      {
        src: "https://drive.google.com/file/d/1tLTqPrMg8bb5Q16bsDs0iF1JPPhTxQ0z/view?usp=sharing",
        date: "2024-10-26",
        type: "video",
        caption: "Great carry and almost my first goal!",
      },
      {
        src: "https://drive.google.com/file/d/1d_7Q9Vx09elIpHyTqZ3FXs6Tq3Tz77SH/view?usp=sharing",
        date: "2024-10-31",
        type: "image",
        caption: "Collapsing after a hard sessions",
      },
      {
        src: "https://drive.google.com/file/d/1SY1VfqXsyJRh9ARr5tKbBgKC0Xb8PgxC/view?usp=sharing",
        date: "2024-11-02",
        type: "video",
        caption: "A funny figure skating shift:)",
      },
      {
        src: "https://drive.google.com/file/d/1O8C0tteuvpOaoZIdCOGJ45OgIBa_qxq0/view?usp=sharing",
        date: "2024-11-16",
        type: "video",
        caption: "Now to watch the big boys play",
      },
      {
        src: "https://drive.google.com/file/d/1fbSu8iWKqob_200nw3HdpMdPj2I-WvpH/view?usp=sharing",
        date: "2024-11-17",
        type: "video",
        caption: "We though it was a goal",
      },
      {
        src: "https://drive.google.com/file/d/12jD6Ae4_qqizIK9wAQoivhShRApclfJs/view?usp=sharing",
        date: "2024-11-23",
        type: "video",
        caption: "Playing the physical game",
      },
      {
        src: "https://drive.google.com/file/d/1xSF2dFd9Hb2BN_HXkahu6flViiL9fWmf/view?usp=sharing",
        date: "2025-01-12",
        type: "video",
        caption: "I'm at the bottom of the pile",
      },
      {
        src: "https://drive.google.com/file/d/1i7PvCOCsHspki795zbBGYg2645ZXRvb5/view?usp=sharing",
        date: "2025-03-01",
        type: "video",
        caption: "Fist-bumps are an important part of the game",
      },
      {
        src: "https://drive.google.com/file/d/12nLO0tr1cwAVOUE0ytSg6UYv9dfzSi6L/view?usp=sharing",
        date: "2025-03-10",
        type: "video",
        caption: "Practicing my turns",
      },
      //{ src: "https://drive.google.com/file/d/1L9tbGwqSM9izgPyAKsrVHMnamRwCUect/view?usp=sharing", date: "2025-03-30", type: "video", caption: "Figure skating while the rest play the game" },
      {
        src: "https://drive.google.com/file/d/13WSSJN_XJE8mSvDNcwAbqAsBgmk4mn-5/view?usp=sharing",
        date: "2025-04-12",
        type: "video",
        caption: "Saluting the fans",
      },
      //{ src: "https://drive.google.com/file/d/1S_oO7CnkTga4sypmI25yinPCSHrs1xjk/view?usp=sharing", date: "2025-04-27", type: "video", caption: "Telling dad that coach put me in defense" },
      {
        src: "https://drive.google.com/file/d/1sJw3sMcCAvgzpRq9S9h9ZEMPm5KSFjOO/view?usp=sharing",
        date: "2025-05-10",
        type: "video",
        caption: "Grit",
      },
      {
        src: "https://drive.google.com/file/d/1PvqOhBPEGH-qp117oeP3C8CB_pUn3igb/view?usp=sharing",
        date: "2025-05-10",
        type: "video",
        caption: "My goal against Bristol",
      },
      {
        src: "https://drive.google.com/file/d/1MIV57Ybk4WxMrGS7fP6u0gsZZaAYxErz/view?usp=sharing",
        date: "2025-05-11",
        type: "video",
        caption: "My goal against Streatham",
      },
      {
        src: "https://drive.google.com/file/d/1Zs7ik_Fb7LFuLhE8weI4Aewd815iIsYN/view?usp=sharing",
        date: "2025-05-11",
        type: "video",
        caption: "Playing the puck out to teammate",
      },
      {
        src: "https://drive.google.com/file/d/1kuk8ns9U1-nLRx8hm_0p5tj7lVE7wUjm/view?usp=sharing",
        date: "2025-05-18",
        type: "video",
        caption: "Carrying and cutting in for the shot!",
      },
      /* { src: "https://drive.google.com/file/d/1OuQ0DEf8RWyFB1H3JwgpZCpJLSkusTfx/view?usp=sharing", date: "2025-05-18", type: "video", caption: "My end to end puck carry" }, */
      {
        src: "https://drive.google.com/file/d/1XuZc2Hi_vY1k4NmLxtWGaMHe0XrfleQL/view?usp=sharing",
        date: "2025-05-18",
        type: "video",
        caption: "My goal against Guildford",
      },
      {
        src: "https://drive.google.com/file/d/1tyuYNFoLury4jMxPHHfK9h_SsKWzXpDw/view?usp=sharing",
        date: "2025-07-05",
        type: "video",
        caption: "Scoring 2 goals in a scrimmage",
      },
    ],
  },
  // add more seasons here
];

function getFileUrl(item: GalleryItem) {
  const match = item.src.match(/\/d\/([^/]+)/);

  if (!match) {
    throw new Error("Invalid Google Drive share URL");
  }
  const fileId = match[1];
  return {
    thumbnail: `https://drive.google.com/thumbnail?id=${fileId}`,
    iframe: `https://drive.google.com/file/d/${fileId}/preview`,
  };
}

function Gallery() {
  const [selectedIdx, setSelectedIdx] = useState<null | number>(null);
  // TODO: change this to switch seasons
  const items = GALLERIES[0].items;

  const openModal = (idx: number) => setSelectedIdx(idx);
  const closeModal = () => setSelectedIdx(null);
  const showPrev = () =>
    selectedIdx !== null &&
    setSelectedIdx((selectedIdx + items.length - 1) % items.length);
  const showNext = () =>
    selectedIdx !== null && setSelectedIdx((selectedIdx + 1) % items.length);

  return (
    <>
      <section className="container mx-auto px-4 mb-12">
        <h2 className="text-2xl font-semibold text-myflame mb-4">
          {GALLERIES[0].title}
        </h2>

        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4 gap-4">
          {items.map((item, idx) => {
            const preview = getFileUrl(item);
            return (
              <div
                key={item.src}
                className="cursor-pointer"
                onClick={() => openModal(idx)}
              >
                {item.type === "image" ? (
                  <img
                    src={preview.thumbnail}
                    alt={item.caption}
                    className="w-full h-32 object-cover rounded-lg"
                  />
                ) : (
                  <img
                    src={playUrl}
                    alt={item.caption}
                    className="w-full h-32 object-cover rounded-lg"
                  />
                )}
                <p className="text-sm text-center mt-2">{item.caption}</p>
                <p className="text-xs text-center text-gray-400">{item.date}</p>
              </div>
            );
          })}
        </div>
      </section>

      {selectedIdx !== null && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center bg-black/75 p-4"
          onClick={closeModal}
        >
          <div
            className="relative bg-bg p-6 rounded-lg max-w-[90vw] max-h-[90vh] w-full sm:w-[600px] overflow-auto"
            onClick={(e) => e.stopPropagation()}
          >
            {/* Prev/Next */}
            <button
              onClick={showPrev}
              className="
                                     absolute left-4 top-1/2 transform -translate-y-1/2
    z-10
    p-3
    bg-white bg-opacity-90 hover:bg-opacity-100
    rounded-full shadow-lg
    focus:outline-none focus:ring-2 focus:ring-myflame
    text-2xl text-gray-800   /* <<< ensure large, dark arrows */
                                    "
            >
              ‹
            </button>
            <button
              onClick={showNext}
              className="
                                       absolute right-4 top-1/2 transform -translate-y-1/2
    z-10
    p-3
    bg-white bg-opacity-90 hover:bg-opacity-100
    rounded-full shadow-lg
    focus:outline-none focus:ring-2 focus:ring-myflame
    text-2xl text-gray-800   /* <<< ensure large, dark arrows */
                                    "
            >
              ›
            </button>

            {/* Responsive iframe */}
            <div className="w-full aspect-video">
              <iframe
                src={getFileUrl(items[selectedIdx]).iframe}
                className="w-full h-full"
                allow="autoplay"
                allowFullScreen
                title={items[selectedIdx].caption}
              />
            </div>

            <h3 className="text-xl font-semibold text-myflame mt-4">
              {items[selectedIdx].caption}
            </h3>
            <p className="text-sm text-gray-400">{items[selectedIdx].date}</p>
          </div>
        </div>
      )}
    </>
  );
}

export default Gallery;
