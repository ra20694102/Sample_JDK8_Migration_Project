# JDK Migration Plan

## 🧾 EXECUTIVE SUMMARY

This project is a small Spring application targeting JDK 8. The application code is clean and does not use internal JDK APIs, but the dependency stack is the primary blocker.

The highest-priority migration work is:
- Upgrade Spring Framework from `4.3.30.RELEASE` to `5.3.20` for JDK 11 compatibility
- Keep JUnit 4 for JDK 11 migration, and plan JUnit 5 migration for JDK 17
- Remove `commons-logging` transitively when upgrading Spring
- Preserve current tests and coverage while upgrading dependencies

The migration is split into two phases:
- **Phase 1**: JDK 8 → JDK 11 stabilization with Spring 5.3.x
- **Phase 2**: JDK 11 → JDK 17 modernization with Spring 6.x and JUnit 5

---

## 🔴 CRITICAL FIX PLAN

### Critical Issue: Spring Framework 4.3.30.RELEASE

**Root cause**
- Spring 4.3 was released before JPMS and relies on internal JDK APIs such as `sun.reflect.*`, `sun.misc.Unsafe`, and `sun.security.action.*`.

**Affected modules**
- `org.springframework:spring-context`
- `org.springframework:spring-aop`
- `org.springframework:spring-beans`
- `org.springframework:spring-core`
- `org.springframework:spring-expression`

**Fix strategy**
- Phase 1: Replace `spring-context` and related Spring modules with `5.3.20`.
- Phase 2: For JDK 17, replace with Spring `6.0.13` or later.

**Migration phase**
- JDK 11: Spring `5.3.20`
- JDK 17: Spring `6.0.13`

**Implementation**
```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.3.20</version>
</dependency>
```

If moving to JDK 17 later:
```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>6.0.13</version>
</dependency>
```

---

## 📦 DEPENDENCY UPGRADE PLAN

### Current dependencies and target versions

| Dependency | Current | JDK 11 Target | JDK 17 Target | Action |
|---|---|---|---|---|
| `org.springframework:spring-context` | `4.3.30.RELEASE` | `5.3.20` | `6.0.13` | Upgrade |
| `org.springframework:spring-core` | `4.3.30.RELEASE` | `5.3.20` | `6.0.13` | Upgrade |
| `org.springframework:spring-beans` | `4.3.30.RELEASE` | `5.3.20` | `6.0.13` | Upgrade |
| `org.springframework:spring-aop` | `4.3.30.RELEASE` | `5.3.20` | `6.0.13` | Upgrade |
| `org.springframework:spring-expression` | `4.3.30.RELEASE` | `5.3.20` | `6.0.13` | Upgrade |
| `commons-logging:commons-logging` | `1.2` | Exclude | Exclude | Replace with SLF4J |
| `org.apache.commons:commons-lang3` | `3.12.0` | Keep | Keep | No action |
| `junit:junit` | `4.13.2` | Keep | Replace with JUnit 5 | Optional/required |
| `org.hamcrest:hamcrest-core` | `1.3` | Keep | Remove | Optional |

### Dependency classification

- **Upgrade**: Spring modules
- **Replace**: `commons-logging` → `jcl-over-slf4j` + `slf4j-api`
- **Keep for JDK 11**: JUnit 4.13.2, Hamcrest 1.3
- **Plan to replace for JDK 17**: JUnit 5.9.x, AssertJ or built-in assertions

### Dependency plan details

1. Upgrading Spring to 5.3.20 will automatically update transitive dependencies such as ASM, CGLIB, and AOP Alliance to JDK 11-compatible versions.
2. Exclude `commons-logging` from Spring core when upgrading:
```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <version>5.3.20</version>
  <exclusions>
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
3. Add SLF4J bridge:
```xml
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>jcl-over-slf4j</artifactId>
  <version>1.7.36</version>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>1.7.36</version>
</dependency>
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.13</version>
</dependency>
```

4. For JDK 17 and Spring 6, migrate tests to JUnit 5:
```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <version>5.9.3</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <version>5.9.3</version>
  <scope>test</scope>
</dependency>
```

---

## 🧪 INTERNAL API MIGRATION PLAN

### Current status
- Application code does not use `sun.*` or `com.sun.*` directly.
- Internal API usage is only present in the Spring 4 dependency stack.

### Plan
1. Phase 1: Upgrade Spring to 5.3.20. This eliminates the Spring 4 internal API dependency on JDK 11 and removes the need for workarounds.
2. Phase 2: If JDK 17 migration is pursued, use Spring 6.0.13, which is designed for JDK 17+.
3. No direct `sun.*` replacement is required in application code because the issue is dependency-driven.

### Temporary workaround if needed
- Use `--add-opens` or `--add-exports` only as an emergency fallback during testing, not as a long-term solution.
- Example runtime flag (only if Spring 4 must be temporarily executed on JDK 11):
```bash
--add-opens java.base/java.lang=ALL-UNNAMED
```

### Must fix before JDK 17
- The Spring 4 dependency must be replaced before JDK 17 migration.
- JUnit 4 must be replaced with JUnit 5 before Spring 6 migration.

---

## 🔧 CODE REFACTORING PLAN

### Required changes

1. **No application-level internal API refactor needed**
   - Application code is already clean and uses modern Java 8 patterns.

2. **Spring configuration review**
   - Verify `AppConfig` and bean definitions compile under Spring 5.3.
   - Ensure `AnnotationConfigApplicationContext` usage in `ApplicationTest` remains valid.

3. **Logging modernization**
   - Replace `commons-logging` transitive dependency with SLF4J features.

4. **JUnit 5 preparation for JDK 17**
   - Update tests after Phase 1 if migrating to Spring 6:
     - Change `@Test` imports to JUnit Jupiter
     - Use `assertThrows()` instead of JUnit 4 exception rules
     - Optionally adopt AssertJ for richer assertions

### Minimal code changes expected
- No production Java code changes required for JDK 11 migration.
- Most changes are dependency and build configuration only.
- If JUnit 5 migration occurs, test code will need annotation and assertion updates.

---

## ⚙️ BUILD & RUNTIME PLAN

### Maven and compiler settings

1. Keep Maven 3.9.14.
2. For JDK 11 migration, maintain `maven.compiler.source` and `target` at `1.8` or update to `11` if desired.
3. For JDK 17 migration, switch to:
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```
4. Use `maven-compiler-plugin` 3.8.1 or newer.

### Build steps

- Phase 1: `mvn clean compile`
- Phase 1: `mvn clean test`
- Phase 2: `mvn clean install -Pjdk17` (if a profile is created for JDK 17)

### JVM flags

- **Temporary**: use `--add-opens` only if Spring 4 still needs to run during transition.
- **Final JDK 11/17**: no extra `--add-opens` should be necessary after dependency upgrade.

### Docker / CI updates

- Update CI agents to include JDK 11 for Phase 1.
- Add JDK 17 to CI for Phase 2.
- Ensure build images include Maven 3.9.x.

---

## 🧪 TEST STRATEGY

### Regression scope
- Run all existing tests: `ApplicationTest`, `UserTest`, `GreetingServiceTest`, `AppConfigTest`, `UserRepositoryTest`.
- Validate core Spring context initialization and business logic.

### Compatibility testing
- Phase 1: run on JDK 8 and JDK 11.
- Phase 2: run on JDK 17 after Spring 6 and JUnit 5 migration.

### Performance benchmarks
- Compare key startup and test suite performance between JDK 8 and JDK 11.
- Confirm no unexpected regressions after Spring dependency upgrade.

### Canary rollout
- Deploy JDK 11 version to a non-critical environment first.
- Monitor startup, logs, and test-backed health checks.
- Only promote to production after stable verification.

---

## 🚀 MIGRATION ROADMAP (PHASED)

### Phase 1 — Preparation
- [ ] Backup current branch and create feature branch.
- [ ] Confirm current build passes on JDK 8.
- [ ] Review `pom.xml` and current Spring dependency graph.
- [ ] Add SLF4J dependencies and exclude `commons-logging`.

### Phase 2 — JDK 11 Migration
- [ ] Upgrade Spring dependencies to `5.3.20`.
- [ ] Run `mvn clean compile`.
- [ ] Run `mvn clean test`.
- [ ] Verify all 5 tests pass.
- [ ] Confirm JaCoCo coverage remains high.
- [ ] Validate runtime on JDK 11.

### Phase 3 — Stabilization
- [ ] Resolve any Spring 5 migration warnings.
- [ ] Verify logging works with SLF4J.
- [ ] Review transitive dependency updates.
- [ ] Document changes and update README if needed.

### Phase 4 — JDK 17 Migration
- [ ] Upgrade Spring to `6.0.13`.
- [ ] Migrate tests to JUnit 5.9.x.
- [ ] Update Maven compiler to Java 17.
- [ ] Run `mvn clean test` on JDK 17.
- [ ] Validate no Spring 6 compatibility issues.

### Phase 5 — Modernization (optional)
- [ ] Consider records or `var` usage after JDK 17 conversion.
- [ ] Clean up legacy test libraries.
- [ ] Remove temporary compatibility flags.

---

## ⚠️ RISKS & MITIGATIONS

### Risk: Spring 5 Behavioral Changes
- **Mitigation**: Run `mvn clean compile` first, then test. The app is simple and low risk.

### Risk: JUnit 4 → JUnit 5 migration
- **Mitigation**: Keep JUnit 4 for Phase 1 and only migrate during Phase 4.

### Risk: Logging transitives
- **Mitigation**: Exclude `commons-logging` and add SLF4J bridge before JDK 11 migration.

### Risk: Transitive dependency conflicts
- **Mitigation**: Use Maven dependency tree after Spring upgrade and override versions if needed.

### Risk: Build system drift
- **Mitigation**: Keep Maven 3.9.14 and update only compiler target versions.

---

## ✅ SUCCESS CRITERIA

- [ ] Spring upgraded to `5.3.20` and JDK 11 build passes.
- [ ] All tests pass on JDK 11.
- [ ] JaCoCo coverage remains above 90%.
- [ ] `commons-logging` is excluded and SLF4J is integrated.
- [ ] JDK 17 upgrade succeeds with Spring 6 and JUnit 5.
- [ ] No direct internal JDK API usage remains in application code.
- [ ] Repository remains clean and version-controlled.
