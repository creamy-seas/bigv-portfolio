import App from "./App";
import "./style.css";

async function bootstrap() {
  const { createRoot } = await import("react-dom/client");
  const container = document.getElementById("root")!;
  const root = createRoot(container);
  root.render(<App />);
}

bootstrap();
