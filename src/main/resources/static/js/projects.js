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
const baseImageUrl = "https://personal-portfolio-z8w0.onrender.com"; // Temel API adresini burada tut

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

function loadProjectList() {
    fetch(`${baseImageUrl}/project`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(data => {
            if (data.result) {
                const projects = data.data;
                if (projects.length === 0) {
                    projectListDiv.innerHTML = "<p>Henüz proje yok.</p>";
                    return;
                }
                let html = "<ul class='list-group'>";
                projects.forEach(p => {
                    // imageUrl değeri kesin "/img/..." şeklinde geldiğini varsayıyoruz
                    // Bazen backend yanlış dönebilir, ona karşı kontrol edebiliriz:
                    const imageUrl = p.imageUrl
                        ? (p.imageUrl.startsWith("http") ? p.imageUrl : baseImageUrl + p.imageUrl)
                        : `${baseImageUrl}/img/Image-not-found.png`;

                    html += `<li class="list-group-item list-group-item-dark" data-id="${p.id}" style="cursor:pointer;">
                    <strong>${p.title}</strong><br/>
                    ${p.description}<br/>
                    <img src="${imageUrl}" alt="Proje Görseli" style="max-width: 150px; max-height: 100px;"
                         onerror="this.onerror=null; this.src='${baseImageUrl}/img/Image-not-found.png';" /><br/>
                    <a href="${p.githubUrl}" target="_blank" rel="noopener noreferrer">Github</a>
                    <button data-id="${p.id}" class="btn btn-danger btn-sm float-end delete-btn">Sil</button>
                </li>`;
                });
                html += "</ul>";
                projectListDiv.innerHTML = html;

                // Tıklayınca formu doldur
                document.querySelectorAll("#projectList li").forEach(li => {
                    li.addEventListener("click", (e) => {
                        if (e.target.classList.contains("delete-btn")) return; // Sil butonunu yakalamıyoruz
                        const selectedId = li.getAttribute("data-id");
                        const selected = projects.find(pr => pr.id == selectedId);
                        if (selected) {
                            projectIdInput.value = selected.id;
                            titleInput.value = selected.title;
                            descriptionInput.value = selected.description;
                            githubUrlInput.value = selected.githubUrl;
                            imageUrlInput.required = false; // Güncellemede resim isteğe bağlı
                            showMessage("Proje formu güncelleme için yüklendi.");
                        }
                    });
                });

                // Silme butonlarına event
                document.querySelectorAll(".delete-btn").forEach(btn => {
                    btn.addEventListener("click", (e) => {
                        e.stopPropagation();
                        const delId = btn.getAttribute("data-id");
                        if (confirm("Projeyi silmek istediğinize emin misiniz?")) {
                            fetch(`${baseImageUrl}/project/admin/delete/${delId}`, {
                                method: "DELETE",
                                headers: {
                                    Authorization: `Bearer ${token}`
                                }
                            })
                                .then(res => res.json())
                                .then(resData => {
                                    if (resData.result) {
                                        showMessage("Proje başarıyla silindi.");
                                        if (projectIdInput.value == delId) clearForm();
                                        loadProjectList();
                                    } else {
                                        showMessage("Silme işlemi başarısız.", true);
                                    }
                                })
                                .catch(err => {
                                    showMessage("Sunucu hatası: " + err.message, true);
                                });
                        }
                    });
                });

            } else {
                projectListDiv.innerHTML = "<p>Projeler yüklenirken hata oluştu.</p>";
            }
        })
        .catch(err => {
            projectListDiv.innerHTML = "<p>Sunucuya bağlanılamadı.</p>";
        });
}

projectForm.addEventListener("submit", (e) => {
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

    fetch(url, {
        method: method,
        headers: {
            Authorization: `Bearer ${token}`
            // NOT: FormData kullanıldığı için 'Content-Type' HEADER'I EKLEMEYİN
        },
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.result) {
                showMessage("Proje başarıyla kaydedildi.");
                clearForm();
                loadProjectList();
            } else {
                showMessage("Kaydetme sırasında hata oluştu.", true);
            }
        })
        .catch(err => {
            showMessage("Sunucu hatası: " + err.message, true);
        });
});

clearBtn.addEventListener("click", clearForm);

loadProjectList();
