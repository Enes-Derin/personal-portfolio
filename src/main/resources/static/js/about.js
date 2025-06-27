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

function loadAboutList() {
  fetch("http://localhost:8080/about", {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(res => res.json())
      .then(data => {
        if (data.result) {
          const abouts = data.data;
          if (!abouts || abouts.length === 0) {
            aboutListDiv.innerHTML = "<p>Henüz bilgi yok.</p>";
            return;
          }

          const first = abouts[0];
          aboutIdInput.value = first.id;
          descriptionInput.value = first.description;
          cvUrlInput.value = first.cvUrl;
          imageUrlInput.required = false;

          let html = "<ul class='list-group'>";
          abouts.forEach(item => {
            const baseImageUrl = "http://localhost:8080";
            html += `<li class="list-group-item list-group-item-dark" style="cursor:pointer" data-id="${item.id}">
                    <strong>Açıklama:</strong> ${item.description}<br/>
                    <strong>CV:</strong> <a href="${item.cvUrl}" target="_blank">Görüntüle</a><br/>
                    <img src="${baseImageUrl + item.imageUrl}" alt="Profil" style="max-width:100px;"><br/>
                   </li>`;
          });
          html += "</ul>";
          aboutListDiv.innerHTML = html;

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
      })
      .catch(err => {
        aboutListDiv.innerHTML = "<p>Sunucuya bağlanılamadı.</p>";
      });
}

aboutForm.addEventListener("submit", function (e) {
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
      ? `http://localhost:8080/about/admin/${id}`
      : "http://localhost:8080/about/admin";

  const method = isUpdate ? "PUT" : "POST";

  fetch(url, {
    method,
    headers: {
      Authorization: `Bearer ${token}`
    },
    body: formData
  })
      .then(res => res.json())
      .then(data => {
        if (data.result) {
          showMessage(isUpdate ? "Güncellendi." : "Kaydedildi.");
          clearForm();
          loadAboutList();
        } else {
          showMessage("Hata: " + (data.errorMessage || ""), true);
        }
      })
      .catch(err => {
        showMessage("Sunucu hatası: " + err.message, true);
      });
});

clearBtn.addEventListener("click", clearForm);

loadAboutList();
