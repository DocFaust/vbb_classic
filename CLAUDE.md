# VBB Classic — Claude Code project context

## Project

VBB Classic is an existing Java EE 8 application. It uses WildFly, JSF/PrimeFaces, JPA, MariaDB and Flyway. The repository includes Java tests and XHTML views. This is a legacy application used as a controlled experiment for agentic development and potentially a future migration to Spring Boot and React.

**Do not start or assume approval for a migration.** A possible target (Spring Boot 4, Java 25, React/TypeScript/Vite) was discussed, but no implementation has been authorized. Reassess requirements and compatibility before proposing migration work.

## Working principles

- Work in small, self-contained iterations.
- Before larger changes, present an implementation plan and wait for approval.
- Make only requested changes; no unsolicited refactoring, modernization, or dependency upgrades.
- Ask about unclear business rules rather than inventing behavior.
- Prefer KISS and YAGNI; follow existing architecture, naming, and conventions.
- Do not add dependencies without a clear, justified benefit.
- Write maintainable, readable code.
- Cover changes with appropriate automated tests, ideally reproducing defects before fixing them.
- Run relevant tests and available checks before reporting completion; distinguish executed checks from assumptions.
- Fix root causes; never bypass failing tests or quality checks to make them pass.
- Protect existing functionality and data. Do not alter production data.
- Never put credentials or secrets into code, logs, or documentation.
- Do not commit, push, create branches, or open PRs without explicit authorization.
- Before editing, inspect the actual repository state and current branch; do not assume prior conversation context is available.

## Recent work: booking cutoff

A Claude Code session investigated a suspected defect in `src/main/java/de/docfaust/vbb/service/CutOffServiceImpl.java`.

Reported root cause: the cutoff carry-forward balance included *all* player bookings, while the cutoff only deleted bookings before the cutoff date. Later bookings could therefore be counted both in the carry-forward entry and as retained original entries. Repeating a cutoff at the same timestamp could also double-count balances.

Reported fix: calculate the carry-forward using only bookings that the cutoff actually deletes, without changing deletion rules. Regression tests were added in `src/test/java/de/docfaust/vbb/service/TestCutOffService.java`.

The previous agent reported five failing regression cases before the fix, then 7/7 cutoff tests and 272/272 overall tests passing afterward. These results were reported by the agent, not independently verified here. Tests used HSQLDB, not production MariaDB.

A branch named `fix/cutoff-balance` was created and pushed. **Verify whether it contains the actual fix commit and whether a PR exists**; an initial commit failed due to missing Git author identity, and the first push therefore contained no code changes. The subsequent outcome is not established in this document.

## Known risks / investigation leads (not approved tasks)

- Cutoff operations may lack an overarching transaction; partial writes are possible if an operation fails.
- Old bookings without an associated game may trigger a null-pointer exception during cutoff after earlier writes.
- The test schema reportedly contains a `START_END_IDX` unique index not present in Flyway migrations or `createDDL.sql`; verify before relying on it.
- Repeating a cutoff may create redundant cutoff seasons/games/zero-value entries even if balances remain stable.
- Existing production data could already contain incorrect historical carry-forwards; code changes do not automatically repair data.
- Historical analysis flagged legacy password hashing, credentials, token handling, and dependency vulnerabilities. Treat these as investigation leads, not confirmed current findings or permission for broad changes.

## Starting a new session

1. Inspect `git status`, branch, recent commits, and relevant project documentation.
2. Read only the code necessary for the assigned task; avoid loading unrelated files into context.
3. Summarize your understanding and any uncertainties before making significant changes.
4. Do not assume an old analysis or previous session is still in context; verify claims against the current source.
