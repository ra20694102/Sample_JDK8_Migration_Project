# Sample_JDK8_Migration_Project

#step 1: Build the Maven project
#step 2: Run Analyze.sh
#step 3: @workspace Follow ExtractorAgent.md
         Start analysis using available reports
#step 4: @workspace Review Report.md for completeness and missing risks
#step 5: @workspace Execute PlannerAgent.md using Report.md as input.
         Generate MigrationPlan.md.

#step 6:
@workspace Act as ExecutorAgent.md.

Use migration-state.json for persistence.

Process ONE module at a time using DAG rules.

Follow loop:
PLAN → ACT → OBSERVE → REFLECT → UPDATE STATE → LOOP

Run build and tests after each module.

Start with:
Load state and build DAG.