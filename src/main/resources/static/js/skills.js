const skillListDiv = document.getElementById("skillList");
const skillForm = document.getElementById("skillForm");
const skillMessageDiv = document.getElementById("skillMessage");
const clearSkillBtn = document.getElementById("clearSkillForm");
const skillIdInput = document.getElementById("skillId");
const nameInput = document.getElementById("name");
const iconUrlInput = document.getElementById("iconUrl");
const categoryInput = document.getElementById("category");

const token = localStorage.getItem("accessToken");

function showSkillMessage(text, isError = false) {
  skillMessageDiv.textContent = text;
  skillMessageDiv.style.color = isError ? "red" : "lightgreen";
}

function clearSkillForm() {
  skillIdInput.value = "";
  nameInput.value = "";
  iconUrlInput.value = "";
  categoryInput.value = "";
  showSkillMessage("");
}

function loadSkills() {
  fetch("http://localhost:8080/skill", {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
      .then(res => res.json())
      .then(data => {
        if(data.result) {
          const skills = data.data;
          if(skills.length === 0){
            skillListDiv.innerHTML = "<p>Henüz yetenek yok.</p>";
            return;
          }
          let html = "<ul class='list-group'>";
          skills.forEach(s => {
            html += `<li class="list-group-item list-group-item-dark" data-id="${s.id}" style="cursor:pointer;">
          <strong>${s.name}</strong> - ${s.category} <br/>
          <i class="${s.iconUrl}" style="font-size: 24px; margin-right: 8px; color: #d4af37;"></i>
          <button data-id="${s.id}" class="btn btn-danger btn-sm float-end delete-skill-btn">Sil</button>
        </li>`;
          });
          html += "</ul>";
          skillListDiv.innerHTML = html;

          document.querySelectorAll("#skillList li").forEach(li => {
            li.addEventListener("click", (e) => {
              if(e.target.classList.contains("delete-skill-btn")) return;
              const selectedId = li.getAttribute("data-id");
              const selected = skills.find(sk => sk.id == selectedId);
              if(selected){
                skillIdInput.value = selected.id;
                nameInput.value = selected.name;
                iconUrlInput.value = selected.iconUrl;
                categoryInput.value = selected.category;
                showSkillMessage("Yetenek formu güncelleme için yüklendi.");
              }
            });
          });

          document.querySelectorAll(".delete-skill-btn").forEach(btn => {
            btn.addEventListener("click", (e) => {
              e.stopPropagation();
              const delId = btn.getAttribute("data-id");
              if(confirm("Yetenek silinsin mi?")) {
                fetch(`http://localhost:8080/skill/admin/${delId}`, {
                  method: "DELETE",
                  headers: {
                    Authorization: `Bearer ${token}`
                  }
                })
                    .then(res => res.json())
                    .then(resData => {
                      if(resData.result){
                        showSkillMessage("Yetenek başarıyla silindi.");
                        if(skillIdInput.value == delId) clearSkillForm();
                        loadSkills();
                      } else {
                        showSkillMessage("Silme işlemi başarısız.", true);
                      }
                    })
                    .catch(err => {
                      showSkillMessage("Sunucu hatası: " + err.message, true);
                    });
              }
            });
          });

        } else {
          skillListDiv.innerHTML = "<p>Yetenekler yüklenirken hata oluştu.</p>";
        }
      })
      .catch(err => {
        skillListDiv.innerHTML = "<p>Sunucuya bağlanılamadı.</p>";
      });
}

skillForm.addEventListener("submit", e => {
  e.preventDefault();
  const id = skillIdInput.value;
  const method = id ? "PUT" : "POST";
  const url = id ? `http://localhost:8080/skill/admin/${id}` : "http://localhost:8080/skill/admin";

  const skillData = {
    name: nameInput.value.trim(),
    iconUrl: iconUrlInput.value.trim(),
    category: categoryInput.value.trim()
  };

  fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify(skillData)
  })
      .then(res => res.json())
      .then(data => {
        if(data.result){
          showSkillMessage("Yetenek başarıyla kaydedildi.");
          clearSkillForm();
          loadSkills();
        } else {
          showSkillMessage("Kaydetme sırasında hata oluştu.", true);
        }
      })
      .catch(err => {
        showSkillMessage("Sunucu hatası: " + err.message, true);
      });
});

clearSkillBtn.addEventListener("click", clearSkillForm);

loadSkills();
