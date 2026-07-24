# Northstar Campaign Concept Studio

A full-stack campaign workspace for marketing teams. A short brief becomes one concise creative concept, three copy variants, a launch checklist, two production-ready image prompts, and two generated campaign visuals.

The application uses Spring Boot 3.5 and a responsive, dependency-free browser UI. All AI work uses the current OpenAI **Responses API** (`POST /v1/responses`)—there is no legacy Completions or Chat Completions code.

## Architecture and security boundary

```text
Browser UI
  │  POST /api/campaigns/generate (brief only)
  ▼
Spring Boot server
  ├─ Responses API + Structured Outputs → concept, copy, checklist, image prompts
  └─ Responses API + image_generation tool → generated images
  │
  ▼
Campaign JSON + base64 image data returned to browser
```

The browser never imports an OpenAI SDK, calls `api.openai.com`, or sees `OPENAI_API_KEY`. The key is read only by Spring from the server environment. In production, keep the service behind authentication and rate limiting, avoid logging request bodies containing confidential briefs, and use your platform’s secret manager.

## Prerequisites

- Java 25 (matching the current `pom.xml`)
- Maven, or the included Maven wrapper
- An OpenAI API key with access to the configured models
- The existing Firebird database if you also use the legacy banking routes

## Environment setup

PowerShell:

```powershell
$env:OPENAI_API_KEY="your_api_key_here"
.\mvnw.cmd spring-boot:run
```

macOS/Linux:

```bash
export OPENAI_API_KEY="your_api_key_here"
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

Never commit the API key. The application intentionally fails the campaign request with a clear configuration message when the key is absent.

## Configuration

All tuning points are in `src/main/resources/application.properties`:

| Environment variable | Default | Purpose |
| --- | --- | --- |
| `OPENAI_API_KEY` | none | Required server credential |
| `OPENAI_BASE_URL` | `https://api.openai.com/v1` | API base URL |
| `OPENAI_TEXT_MODEL` | `gpt-5.6-sol` | Responses model for strategy and copy |
| `OPENAI_IMAGE_MODEL` | `gpt-5.6` | Responses model used with the image-generation tool |
| `OPENAI_IMAGE_QUALITY` | `medium` | Image-generation quality |
| `OPENAI_IMAGE_SIZE` | `1536x1024` | Campaign image aspect and resolution |

To adjust behavior later:

- **Models:** change `OPENAI_TEXT_MODEL` or `OPENAI_IMAGE_MODEL`; check the [OpenAI model catalog](https://developers.openai.com/api/docs/models) and image-generation guide first.
- **Prompt:** edit `INSTRUCTIONS` and `briefInput()` in `OpenAiCampaignService`.
- **Output shape:** edit `campaignSchema()` and the matching `CampaignResult` records.
- **Images:** change `OPENAI_IMAGE_QUALITY`, `OPENAI_IMAGE_SIZE`, the image-direction instructions, or the number of `imageDirections` in the schema.

The service sets `store: false` on Responses API calls. Change that only after reviewing your data-retention requirements.

## Build and test

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

The packaged JAR serves both the API and the UI:

```powershell
java -jar target/home-bank-rest-api-0.0.1-SNAPSHOT.jar
```

## Small validation plan

1. **Happy path:** enter a complete brief, select two channels, and verify one concept, exactly three copy variants, 6–10 checklist items, two prompts, and two images.
2. **Input validation:** omit a required field, exceed a field limit, and submit without a channel; confirm useful browser or API errors.
3. **Credential failure:** run without `OPENAI_API_KEY`; confirm the key is never exposed and the UI shows the server configuration error.
4. **Partial image failure:** simulate an image tool failure; confirm the text campaign remains usable and each failed visual has its own error state.
5. **Responsive/accessibility pass:** test keyboard-only operation at 375px, 768px, and desktop widths; verify labels, focus states, contrast, loading announcement, and image alt text.
6. **Production smoke test:** use a non-sensitive brief, verify TLS, secret injection, request timeout, logs, rate limiting, and expected API spend.

## Deployment

Build the JAR and deploy it to any Java-capable service (Render, Railway, Fly.io, a container platform, or a VM). Configure `OPENAI_API_KEY` as a secret and expose port `8080` (or set `SERVER_PORT`). For multi-instance deployment, no campaign state is stored locally; generated images are returned in the response. For longer-term asset retention, upload decoded image bytes to object storage and return signed URLs instead.

The current repository still contains existing Firebird-backed banking endpoints and configuration. If deploying only the campaign studio, either provide that database or isolate/remove those legacy modules in a separate cleanup.

## API notes

The text stage uses Responses API Structured Outputs through `text.format` with a strict JSON Schema. Image creation uses the Responses API `image_generation` tool and reads base64 output from `image_generation_call.result`, following the current [Responses API](https://developers.openai.com/api/docs/guides/migrate-to-responses), [Structured Outputs](https://developers.openai.com/api/docs/guides/structured-outputs), and [image generation](https://developers.openai.com/api/docs/guides/image-generation) guidance.
