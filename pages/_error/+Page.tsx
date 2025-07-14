export { Page };

const Page = ({ error }: { error: Error }) => (
  <div style={{ padding: 40, fontFamily: "sans-serif" }}>
    <h1>Something crashed badly!</h1>
    <pre style={{ color: "red" }}>{error.message}</pre>
  </div>
);
