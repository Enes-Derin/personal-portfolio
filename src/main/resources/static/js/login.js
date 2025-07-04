document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    document.getElementById("usernameError").textContent = "";
    document.getElementById("passwordError").textContent = "";
    document.getElementById("generalError").textContent = "";

    const username = document.getElementById("inputUsername").value.trim();
    const password = document.getElementById("inputPassword3").value;

    try {
        const response = await fetch("/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        const result = await response.json();

        if (response.ok) {
            localStorage.setItem("accessToken", result.data.accessToken);
            localStorage.setItem("refreshToken", result.data.refreshToken);
            window.location.href = "/admin-panel.html";
        } else {
            const errors = result?.exception?.message;

            // 400 ise: validation hataları varsa input altına yaz
            if (response.status === 400 && typeof errors === "object") {
                if (errors.username) {
                    document.getElementById("usernameError").textContent = errors.username[0];
                }
                if (errors.password) {
                    document.getElementById("passwordError").textContent = errors.password[0];
                }
            } else {
                // Diğer durumlar: (örnek: şifre yanlış, kullanıcı yok vs.)
                const genelMesaj = typeof errors === "string" ? errors : "Giriş başarısız.";
                document.getElementById("generalError").textContent = genelMesaj;
            }
        }
    } catch (error) {
        document.getElementById("generalError").textContent = "Sunucuya bağlanırken hata oluştu: " + error.message;
    }
});