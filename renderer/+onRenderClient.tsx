import React from "react";
import { hydrateRoot } from "react-dom/client";
import type { OnRenderClientSync } from "vike/types";

import Header from "../src/components/Header";
import "../src/style.css";

export const onRenderClient: OnRenderClientSync = ({ Page }) => {
  const PageComponent = Page as React.ComponentType<any>;

  hydrateRoot(
    document.getElementById("root")!,
    <Header>
      <PageComponent />
    </Header>,
  );
};
