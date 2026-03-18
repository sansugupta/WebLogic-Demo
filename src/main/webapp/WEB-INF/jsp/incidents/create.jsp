<%@ include file="../layout/header.jsp" %>

<div class="card">
    <h2>➕ Create New Incident</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/web/incidents" method="post">
        <div class="form-group">
            <label for="title">Title *</label>
            <input type="text" 
                   id="title" 
                   name="title" 
                   value="${incident.title}" 
                   required 
                   placeholder="Enter incident title">
        </div>

        <div class="form-group">
            <label for="description">Description *</label>
            <textarea id="description" 
                      name="description" 
                      required 
                      placeholder="Describe the incident in detail">${incident.description}</textarea>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <c:forEach var="statusOption" items="${statuses}">
                        <option value="${statusOption}" 
                                ${incident.status == statusOption ? 'selected' : ''}>
                            ${statusOption.displayName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="priority">Priority *</label>
                <select id="priority" name="priority" required>
                    <c:forEach var="priorityOption" items="${priorities}">
                        <option value="${priorityOption}" 
                                ${incident.priority == priorityOption ? 'selected' : ''}>
                            ${priorityOption.displayName}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div style="margin-top: 2rem; display: flex; gap: 1rem;">
            <button type="submit" class="btn btn-success">
                💾 Create Incident
            </button>
            <a href="${pageContext.request.contextPath}/web/incidents" class="btn">
                ❌ Cancel
            </a>
        </div>
    </form>
</div>

<!-- Form Validation Info -->
<div class="card">
    <h3>📝 Form Guidelines</h3>
    <ul style="padding-left: 2rem;">
        <li><strong>Title:</strong> Brief, descriptive summary of the incident</li>
        <li><strong>Description:</strong> Detailed explanation of what happened</li>
        <li><strong>Status:</strong> Current state (defaults to "Open")</li>
        <li><strong>Priority:</strong> Impact level (Low, Medium, High, Critical)</li>
    </ul>
</div>

<%@ include file="../layout/footer.jsp" %>