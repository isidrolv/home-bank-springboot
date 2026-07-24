const form = document.querySelector("#campaign-form");
const submitButton = form.querySelector("button[type=submit]");
const states = {
    empty: document.querySelector("#empty-state"),
    loading: document.querySelector("#loading-state"),
    error: document.querySelector("#error-state"),
    result: document.querySelector("#result")
};

const loadingMessages = [
    "Finding the strategic thread…",
    "Writing the copy system…",
    "Art directing the visual world…",
    "Preparing the launch plan…"
];
let loadingTimer;

function showState(name) {
    Object.entries(states).forEach(([key, element]) => element.hidden = key !== name);
}

function startLoading() {
    showState("loading");
    submitButton.disabled = true;
    let index = 0;
    document.querySelector("#loading-title").textContent = loadingMessages[index];
    loadingTimer = window.setInterval(() => {
        index = Math.min(index + 1, loadingMessages.length - 1);
        document.querySelector("#loading-title").textContent = loadingMessages[index];
    }, 9000);
}

function stopLoading() {
    window.clearInterval(loadingTimer);
    submitButton.disabled = false;
}

function getPayload() {
    return {
        brief: document.querySelector("#brief").value.trim(),
        audience: document.querySelector("#audience").value.trim(),
        productDetails: document.querySelector("#product").value.trim(),
        tone: document.querySelector("#tone").value,
        channels: [...document.querySelectorAll(".channel-grid input:checked")].map(input => input.value)
    };
}

function escapeHtml(value = "") {
    return value.replace(/[&<>"']/g, char => ({
        "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#039;"
    })[char]);
}

function renderResult(data) {
    document.querySelector("#concept-name").textContent = data.conceptName;
    document.querySelector("#concept").textContent = data.concept;
    document.querySelector("#strategic-angle").textContent = data.strategicAngle;

    document.querySelector("#copy-variants").innerHTML = data.copyVariants.map((variant, index) => `
        <article class="copy-card">
            <div class="copy-meta"><span>Variant 0${index + 1} / ${escapeHtml(variant.label)}</span><span>${escapeHtml(variant.channelFit)}</span></div>
            <h4>${escapeHtml(variant.headline)}</h4>
            <p>${escapeHtml(variant.body)}</p>
        </article>
    `).join("");

    document.querySelector("#image-grid").innerHTML = data.images.map(image => `
        <figure class="image-card">
            ${image.dataUrl
                ? `<img src="${image.dataUrl}" alt="${escapeHtml(image.title)} campaign visual">`
                : `<div class="image-fallback">${escapeHtml(image.error || "Visual unavailable")}</div>`}
            <figcaption>
                <h4>${escapeHtml(image.title)}</h4>
                <details><summary>View image prompt</summary>${escapeHtml(image.prompt)}</details>
            </figcaption>
        </figure>
    `).join("");

    document.querySelector("#checklist").innerHTML = data.launchChecklist.map(item => `
        <div class="check-item"><span>${escapeHtml(item.phase)}</span><i></i><p>${escapeHtml(item.task)}</p></div>
    `).join("");

    showState("result");
    if (window.innerWidth < 980) states.result.scrollIntoView({ behavior: "smooth", block: "start" });
}

async function generateCampaign(event) {
    event.preventDefault();
    const payload = getPayload();
    if (payload.channels.length === 0) {
        document.querySelector("#error-message").textContent = "Choose at least one campaign channel.";
        showState("error");
        return;
    }

    startLoading();
    try {
        const response = await fetch("/api/campaigns/generate", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await response.json().catch(() => ({}));
        if (!response.ok) throw new Error(data.message || "The campaign service returned an unexpected response.");
        renderResult(data);
    } catch (error) {
        document.querySelector("#error-message").textContent =
            error.message || "Check your connection and try again.";
        showState("error");
    } finally {
        stopLoading();
    }
}

form.addEventListener("submit", generateCampaign);
document.querySelector("#retry-button").addEventListener("click", () => form.requestSubmit());
