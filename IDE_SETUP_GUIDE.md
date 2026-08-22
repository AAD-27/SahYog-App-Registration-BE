# IDE Setup Guide for Lombok

## Problem
The IDE (IntelliJ IDEA, Eclipse, VS Code) doesn't recognize Lombok annotations like `@Data`, `@RequiredArgsConstructor`, `@Entity`, etc., and shows compilation errors or prevents the app from starting.

## Solution

### For IntelliJ IDEA (Recommended)

#### Step 1: Install Lombok Plugin
1. Open **Settings** (or **Preferences** on Mac)
   - Windows/Linux: `File → Settings`
   - Mac: `IntelliJ IDEA → Preferences`
2. Navigate to **Plugins**
3. Search for "Lombok"
4. Click **Install** on the "Lombok" plugin by JetBrains
5. Restart IntelliJ IDEA when prompted

#### Step 2: Enable Annotation Processing
1. Open **Settings/Preferences**
2. Navigate to **Build, Execution, Deployment → Compiler → Annotation Processors**
3. Check the box: **Enable annotation processing**
4. Click **OK**

#### Step 3: Rebuild Project
1. Go to **Build → Rebuild Project**
   - Or use keyboard shortcut: `Ctrl+Shift+F9` (Windows/Linux) or `Cmd+Shift+F9` (Mac)
2. Wait for the build to complete

#### Step 4: Run the Application
1. Open `MsAppRegApplication.java`
2. Click the green **Play** button (or use `Shift+F10`)

### For Eclipse / Spring Tool Suite (STS)

#### Step 1: Install Lombok
1. Download Lombok from: https://projectlombok.org/download
2. Run the installer:
   ```bash
   java -jar lombok.jar
   ```
3. Select your Eclipse/STS installation folder
4. Click **Install/Update**
5. Restart Eclipse/STS

#### Step 2: Enable Annotation Processing
1. Right-click project → **Properties**
2. Search for "Annotation Processing"
3. Enable annotation processing
4. Rebuild project

### For VS Code

#### Step 1: Install Extensions
1. Install **Extension Pack for Java** (Microsoft)
2. Install **Lombok Annotations Support for VS Code**
3. Reload VS Code

#### Step 2: Rebuild
1. Open Command Palette: `Ctrl+Shift+P`
2. Run: **Java: Clean Language Server Workspace**
3. Reload window

### For Command Line / Maven (No IDE Configuration Needed)

You can run the application directly without IDE support:

```bash
# Build
mvn clean install

# Run the application
mvn spring-boot:run

# Or directly with Java
java -jar target/ms-app-reg-1.0.0.jar
```

### Run via Maven in IDE

If IDE still has issues:
1. Open Terminal in IDE
2. Run: `mvn spring-boot:run`
3. App will start on http://localhost:8090

## Troubleshooting

### If still getting "symbol not found" errors:
1. Close IDE completely
2. Delete `.idea` folder (IntelliJ) or `.settings` folder (Eclipse)
3. Reopen project
4. Rebuild project

### If annotation processing still shows errors:
1. Ensure `pom.xml` has Lombok dependency with `<scope>provided</scope>`
2. In IDE settings, set **Compiler → Shared options → Resource patterns** to include `.properties` files
3. Run **Invalidate Caches** (IntelliJ: `File → Invalidate Caches`)

## pom.xml Configuration (Already Included)

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

The `<scope>provided</scope>` means Lombok is only used during compilation and won't be packaged in the final JAR.

## Verification

After setup, you should:
- ✅ See no red squiggles on Lombok annotations
- ✅ See generated getters/setters in IDE autocomplete
- ✅ Be able to click Play button to run `MsAppRegApplication`
- ✅ App starts on port 8090

## Quick Test

Once running, test the API:

```bash
curl -X POST http://localhost:8090/api/register-application/initialize \
  -H "Content-Type: application/json" \
  -d '{"applicationNum":""}'
```

Should return:
```json
{"applicationNum":null,"pageId":null,"firstName":null,"middleName":null,"lastName":null,"mobileNumber":null,"emailAddress":null,"applicationDate":null,"found":false}
```
