// js/data/api.js
const API_URL = "https://unipark-backend-api.azurewebsites.net";

// Funções genéricas de CRUD
export async function getUsuarios() {
    const res = await fetch(`${API_URL}/usuarios`);
    if (!res.ok) throw new Error("Erro ao buscar usuários");
    return res.json();
}

export async function createUsuario(usuario) {
    const res = await fetch(`${API_URL}/usuarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario),
    });
    if (!res.ok) throw new Error("Erro ao criar usuário");
    return res.json();
}

export async function updateUsuario(id, usuario) {
    const res = await fetch(`${API_URL}/usuarios/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario),
    });
    if (!res.ok) throw new Error("Erro ao atualizar usuário");
    return res.json();
}

export async function deleteUsuario(id) {
    const res = await fetch(`${API_URL}/usuarios/${id}`, { method: "DELETE" });
    if (!res.ok) throw new Error("Erro ao deletar usuário");
}
