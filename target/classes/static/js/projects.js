const projectListDiv = document.getElementById("projectList");
const projectForm = document.getElementById("projectForm");
const messageDiv = document.getElementById("message");
const clearBtn = document.getElementById("clearBtn");
const projectIdInput = document.getElementById("projectId");
const titleInput = document.getElementById("title");
const descriptionInput = document.getElementById("description");
const githubUrlInput = document.getElementById("githubUrl");
const imageUrlInput = document.getElementById("imageUrl");

const token = localStorage.getItem("accessToken");
const baseImageUrl = "https://personal-portfolio-z8w0.onrender.com";

function showMessage(text, isError = false) {
    messageDiv.textContent = text;
    messageDiv.style.color = isError ? "red" : "lightgreen";
}

function clearForm() {
    projectIdInput.value = "";
    titleInput.value = "";
    descriptionInput.value = "";
    githubUrlInput.value = "";
    imageUrlInput.value = "";
    imageUrlInput.required = true;
    showMessage("");
}

async function loadProjectList() {
    try {
        const res = await fetch(`${baseImageUrl}/project`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        if (!res.ok) throw new Error(`Sunucu hatası: ${res.status} ${res.statusText}`);

        const data = await res.json();

        if (!data.result) {
            projectListDiv.innerHTML = "<p>Projeler yüklenirken hata oluştu.</p>";
            return;
        }

        const projects = data.data;

        if (!projects || projects.length === 0) {
            projectListDiv.innerHTML = "<p>Henüz proje yok.</p>";
            clearForm();
            return;
        }

        let html = "<ul class='list-group'>";
        projects.forEach((p) => {
            const imageUrl = p.imageUrl
                ? p.imageUrl.startsWith("http")
                    ? p.imageUrl
                    : baseImageUrl + p.imageUrl
                : `${baseImageUrl}/img/Image-not-found.png`;

            html += `
        <li class="list-group-item list-group-item-dark" data-id="${p.id}" style="cursor:pointer; position: relative;">
          <strong>${p.title}</strong><br/>
          ${p.description}<br/>
          <img src="${imageUrl}" alt="Proje Görseli" style="max-width:150px; max-height:100px; margin-top:5px;"
               onerror="this.onerror=null;this.src='${baseImageUrl}/img/Image-not-found.png';" /><br/>
          <a href="${p.githubUrl}" target="_blank" rel="noopener noreferrer">Github</a>
          <button data-id="${p.id}" class="btn btn-danger btn-sm float-end delete-btn" style="position:absolute; top:5px; right:5px;">Sil</button>
        </li>`;
        });
        html += "</ul>";
        projectListDiv.innerHTML = html;

        // Liste elemanlarına tıklama - sil butonu dışındakiler
        document.querySelectorAll("#projectList li").forEach((li) => {
            li.addEventListener("click", (e) => {
                if (e.target.classList.contains("delete-btn")) return; // Sil butonunu atla
                const selectedId = li.getAttribute("data-id");
                const selected = projects.find((pr) => pr.id == selectedId);
                if (selected) {
                    projectIdInput.value = selected.id;
                    titleInput.value = selected.title;
                    descriptionInput.value = selected.description;
                    githubUrlInput.value = selected.githubUrl;
                    imageUrlInput.required = false; // Güncellemede resim opsiyonel
                    showMessage("Proje formu güncelleme için yüklendi.");
                }
            });
        });

        // Silme butonları
        document.querySelectorAll(".delete-btn").forEach((btn) => {
            btn.addEventListener("click", async (e) => {
                e.stopPropagation();
                const delId = btn.getAttribute("data-id");
                if (confirm("Projeyi silmek istediğinize emin misiniz?")) {
                    try {
                        const res = await fetch(`${baseImageUrl}/project/admin/delete/${delId}`, {
                            method: "DELETE",
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                        });
                        const resData = await res.json();

                        if (resData.result) {
                            showMessage("Proje başarıyla silindi.");
                            if (projectIdInput.value === delId) clearForm();
                            loadProjectList();
                        } else {
                            showMessage("Silme işlemi başarısız.", true);
                        }
                    } catch (err) {
                        showMessage("Sunucu hatası: " + err.message, true);
                    }
                }
            });
        });
    } catch (error) {
        projectListDiv.innerHTML = `<p>Sunucuya bağlanılamadı: ${error.message}</p>`;
    }
}

projectForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("title", titleInput.value);
    formData.append("description", descriptionInput.value);
    formData.append("githubUrl", githubUrlInput.value);

    if (imageUrlInput.files[0]) {
        formData.append("imageUrl", imageUrlInput.files[0]);
    }

    const id = projectIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `${baseImageUrl}/project/admin/${id}` : `${baseImageUrl}/project/admin`;

    try {
        const res = await fetch(url, {
            method,
            headers: {
                Authorization: `Bearer ${token}`,
            },
            body: formData,
        });

        if (!res.ok) throw new Error(`Sunucu hatası: ${res.status} ${res.statusText}`);

        const data = await res.json();

        if (data.result) {
            showMessage("Proje başarıyla kaydedildi.");
            clearForm();
            loadProjectList();
        } else {
            showMessage("Kaydetme sırasında hata oluştu.", true);
        }
    } catch (err) {
        showMessage("Sunucu hatası: " + err.message, true);
    }
});

clearBtn.addEventListener("click", clearForm);

loadProjectList();
