# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**
Are there existing APIs or integration points we can leverage for cost data?
How frequently should cost allocations be recalculated (real-time, daily, monthly)?
Are there any regulatory or reporting standards we must comply with?
How should we handle missing, inconsistent, or delayed data?
What level of granularity is needed for reporting? (per warehouse, per SKU, per order, etc.)
Should the allocation logic be configurable via formulas/rules or hardcoded?
Should cost allocation calculations be batch-processed or streamed in real-time?

## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**
Which costs are directly affected by Warehouse and Store management in this system? (example: costs of maintaining warehouses in same location, unused capacity etc)
How do we measure cost savings for each warehouse or store?
Should we track warehouse utilization, stock turnover, and replacement frequency as key metrics?
Can cost optimization rules be automated in the system (e.g., alerts for underused warehouses, overstock)?

Should we implement configurable allocation rules based on stock, capacity, and location?
Which operations generate the highest cost per warehouse/store: creation, replacement, or daily stock management?
Are there historical metrics on warehouse utilization, stock levels, or transfers to stores?
How does distance from stores to warehouses affect transportation costs?

## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**
Are there existing APIs, database views, or ETL pipelines we can leverage?

Is the integration uni-directional (financial → warehouse) or bi-directional (updates back to finance)?

What financial dimensions (cost center, ledger account, product, warehouse) should be included?

Are there mappings needed between warehouse codes/business units and financial account codes?

Are there critical thresholds or business rules we need to enforce automatically (e.g., alerts for cost anomalies or budget overruns)?

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**
What historical data (warehouse costs, stock levels, labor, transportation, inventory turnover) is available, and how reliable is it for forecasting?

Should forecasts be at the warehouse level, store level, product level, or aggregated across regions?

Integration with Other Systems: How will the budgeting system connect with the Cost Control Tool and financial systems to ensure real-time updates and consistent data?

How should the system handle seasonal demand fluctuations, unexpected disruptions, or changes in warehouse utilization?

KPIs and Metrics: Which metrics (e.g., cost per SKU, utilization rate, stock-to-demand ratio) should drive forecasting accuracy and budget allocation decisions?

How will forecast outputs support resource allocation, capital investment decisions, and operational planning?

Which parts of budgeting and forecasting can be automated and where should human judgment intervene for accuracy?

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**
Are there cost patterns from the old warehouse (e.g., peak inventory, seasonal labor spikes) that should inform resource planning and cost projections for the new warehouse?
How will we compare actual costs of the new warehouse against historical benchmarks to detect deviations early and implement corrective actions?
How do we distinguish transition costs (archiving, moving inventory, setup) from ongoing operational costs to avoid misbudgeting?
Can preserving historical cost data help inform decisions about warehouse location, size, or capacity for the new warehouse?
Are there financial or regulatory requirements that mandate preservation of cost history for archived warehouses?
How can historical cost insights guide prioritization of investments (automation, storage systems, labor planning) for the new warehouse?

## Instructions for Candidates
Before starting the case study, read the [BRIEFING.md](BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
