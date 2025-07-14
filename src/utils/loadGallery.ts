import Papa from "papaparse";

export interface GalleryEntry {
    season: string;
    date: Date;
    id: string;
    description: string;
    type: "image" | "video";
}

let dataPromise: Promise<GalleryEntry[]> | null = null;

export function loadGallery(): Promise<GalleryEntry[]> {
    if (!dataPromise) {
        dataPromise = new Promise((resolve) =>
            Papa.parse<GalleryEntry>("data/gallery.csv", {
                header: true,
                download: true,
                skipEmptyLines: true,
                complete: ({ data }) => resolve(data),
                transform: (value, field) =>
                    field === "date" ? new Date(value) : value,
            }),
        );
    }
    return dataPromise;
}
