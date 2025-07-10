const aboutListDiv = document.getElementById("aboutList");
const aboutForm = document.getElementById("aboutForm");
const messageDiv = document.getElementById("message");
const clearBtn = document.getElementById("clearBtn");
const aboutIdInput = document.getElementById("aboutId");
const descriptionInput = document.getElementById("description");
const cvUrlInput = document.getElementById("cvUrl");
const imageUrlInput = document.getElementById("imageUrl");

const token = localStorage.getItem("accessToken");

function showMessage(text, isError = false) {
  messageDiv.textContent = text;
  messageDiv.style.color = isError ? "red" : "lightgreen";
}

function clearForm() {
  aboutIdInput.value = "";
  descriptionInput.value = "";
  cvUrlInput.value = "";
  imageUrlInput.value = "";
  imageUrlInput.required = true;
  showMessage("");
}

async function loadAboutList() {
  try {
    const res = await fetch("https://personal-portfolio-z8w0.onrender.com/about", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!res.ok) {
      throw new Error(`Sunucu hatası: ${res.status} ${res.statusText}`);
    }

    const data = await res.json();

    if (data.result) {
      const abouts = data.data;

      if (!abouts || abouts.length === 0) {
        aboutListDiv.innerHTML = "<p>Henüz bilgi yok.</p>";
        clearForm();
        return;
      }

      // Formu ilk öğe ile doldur
      const first = abouts[0];
      aboutIdInput.value = first.id;
      descriptionInput.value = first.description;
      cvUrlInput.value = first.cvUrl;
      imageUrlInput.required = false;

      // Listeyi oluştur
      const baseImageUrl = "https://personal-portfolio-z8w0.onrender.com";
      let html = "<ul class='list-group'>";
      abouts.forEach(item => {
        html += `
          <li class="list-group-item list-group-item-dark" style="cursor:pointer" data-id="${item.id}">
            <strong>Açıklama:</strong> ${item.description}<br/>
            <strong>CV:</strong> <a href="${item.cvUrl}" target="_blank" rel="noopener noreferrer">Görüntüle</a><br/>
            <img src="${baseImageUrl + item.imageUrl}" alt="Profil" style="max-width:100px; margin-top:5px;"><br/>
          </li>
        `;
      });
      html += "</ul>";
      aboutListDiv.innerHTML = html;

      // Liste elemanlarına tıklama eventi ata
      document.querySelectorAll("#aboutList li").forEach(li => {
        li.addEventListener("click", () => {
          const selectedId = li.getAttribute("data-id");
          const selected = abouts.find(a => a.id == selectedId);
          if (selected) {
            aboutIdInput.value = selected.id;
            descriptionInput.value = selected.description;
            cvUrlInput.value = selected.cvUrl;
            imageUrlInput.required = false;
            showMessage("Güncellemek için form yüklendi.");
          }
        });
      });
    } else {
      aboutListDiv.innerHTML = "<p>Veri alınırken hata oluştu.</p>";
    }
  } catch (error) {
    aboutListDiv.innerHTML = `<p>Sunucuya bağlanılamadı: ${error.message}</p>`;
  }
}

aboutForm.addEventListener("submit", async function (e) {
  e.preventDefault();

  const formData = new FormData();
  formData.append("description", descriptionInput.value);
  formData.append("cvUrl", cvUrlInput.value);

  if (imageUrlInput.files[0]) {
    formData.append("imageUrl", imageUrlInput.files[0]);
  }

  const id = aboutIdInput.value;
  const isUpdate = id !== "";

  const url = isUpdate
      ? `https://personal-portfolio-z8w0.onrender.com/about/admin/${id}`
      : "https://personal-portfolio-z8w0.onrender.com/about/admin";

  const method = isUpdate ? "PUT" : "POST";

  try {
    const res = await fetch(url, {
      method,
      headers: {
        Authorization: `Bearer ${token}`
      },
      body: formData
    });

    if (!res.ok) {
      // JSON dönmeyebilir, önce status kontrolü yapalım
      let errorMsg = `Sunucu hatası: ${res.status} ${res.statusText}`;
      try {
        const errData = await res.json();
        if (errData.errorMessage) errorMsg = `Hata: ${errData.errorMessage}`;
      } catch (_) { /* ignore JSON parse error */ }
      throw new Error(errorMsg);
    }

    const data = await res.json();

    if (data.result) {
      showMessage(isUpdate ? "Güncellendi." : "Kaydedildi.");
      clearForm();
      loadAboutList();
    } else {
      showMessage("Hata: " + (data.errorMessage || "Bilgi alınamadı."), true);
    }
  } catch (error) {
    showMessage("Sunucu hatası: " + error.message, true);
  }
});

clearBtn.addEventListener("click", clearForm);

// Sayfa açılır açılmaz listeyi yükle
loadAboutList();
