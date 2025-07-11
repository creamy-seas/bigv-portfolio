import { evalDynamicDates } from "./utils/loadDate";

const { age, season } = evalDynamicDates("2019-10-15");

export const AGE = age;
export const SEASON = season;
export const TEAM = "Slough Jets U10s";
