import { useEffect, useState } from "react";

export function App() {
  const [status, setStatus] = useState("unbekannt");

  useEffect(() => {
    fetch("/api/health", { credentials: "include" })
      .then((response) => response.json())
      .then((json) => setStatus(json.status ?? "fehler"))
      .catch(() => setStatus("fehler"));
  }, []);

  return (
    <main>
      <h1>VBB Next</h1>
      <p>Backend-Status: {status}</p>
    </main>
  );
}
