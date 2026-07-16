import axios from "axios";

const api = axios.create({
  baseURL: "",
});

export async function importRepository(url: string) {
  const response = await api.post("/repositories", { url });
  return response.data;
}

export async function generateEmbeddings(id: number) {
  const response = await api.post(`/repositories/${id}/embeddings`);
  return response.data;
}

export async function askRepository(id: number, question: string) {
  const response = await api.post(`/repositories/${id}/ask`, null, {
    params: { question },
  });
  return response.data;
}

export async function searchChunks(question: string) {
  const response = await api.get("/repositories/search", {
    params: { question },
  });
  return response.data;
}