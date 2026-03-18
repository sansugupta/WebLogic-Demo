<%@ include file="../layout/header.jsp" %>

<div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2>🔍 Incident Details #${incident.id}</h2>
        <a href="${pageContext.request.contextPath}/web/incidents" class="btn">
            ← Back to List
        </a>
    </div>

    <!-- Display success/error messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            ${successMessage}
        </div>
    </c:if>
    
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            ${errorMessage}
        </div>
    </c:if>

    <!-- Incident Information -->
    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-bottom: 2rem;">
        <div>
            <h3>📋 Basic Information</h3>
            <table style="margin-top: 1rem;">
                <tr>
                    <td style="font-weight: 600; width: 120px;">ID:</td>
                    <td>#${incident.id}</td>
                </tr>
                <tr>
                    <td style="font-weight: 600;">Title:</td>
                    <td>${incident.title}</td>
                </tr>
                <tr>
                    <td style="font-weight: 600;">Status:</td>
                    <td>
                        <span class="status-badge status-${incident.status.name().toLowerCase().replace('_', '-')}">
                            ${incident.status.displayName}
                        </span>
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: 600;">Priority:</td>
                    <td>
                        <span class="priority-${incident.priority.name().toLowerCase()}">
                            ⚡ ${incident.priority.displayName}
                        </span>
                    </td>
                </tr>
            </table>
        </div>

        <div>
            <h3>⏰ Timeline</h3>
            <table style="margin-top: 1rem;">
                <tr>
                    <td style="font-weight: 600; width: 120px;">Created:</td>
                    <td>
                        <fmt:formatDate value="${incident.createdAt}" pattern="MMM dd, yyyy 'at' HH:mm:ss" />
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: 600;">Updated:</td>
                    <td>
                        <fmt:formatDate value="${incident.updatedAt}" pattern="MMM dd, yyyy 'at' HH:mm:ss" />
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <!-- Description -->
    <div style="margin-bottom: 2rem;">
        <h3>📝 Description</h3>
        <div style="background: #f8f9fa; padding: 1.5rem; border-radius: 4px; margin-top: 1rem; border-left: 4px solid #667eea;">
            ${incident.description}
        </div>
    </div>

    <!-- Status Update Form -->
    <div style="background: #f8f9fa; padding: 1.5rem; border-radius: 4px;">
        <h3>🔄 Update Status</h3>
        <form action="${pageContext.request.contextPath}/web/incidents/${incident.id}/status" method="post" 
              style="display: flex; align-items: center; gap: 1rem; margin-top: 1rem;">
            <label for="status" style="margin: 0; font-weight: 600;">New Status:</label>
            <select id="status" name="status" style="width: auto; min-width: 150px;">
                <c:forEach var="statusOption" items="${['OPEN', 'IN_PROGRESS', 'RESOLVED']}">
                    <option value="${statusOption}" 
                            ${incident.status.name() == statusOption ? 'selected' : ''}>
                        <c:choose>
                            <c:when test="${statusOption == 'OPEN'}">Open</c:when>
                            <c:when test="${statusOption == 'IN_PROGRESS'}">In Progress</c:when>
                            <c:when test="${statusOption == 'RESOLVED'}">Resolved</c:when>
                        </c:choose>
                    </option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-warning" style="padding: 0.5rem 1rem;">
                Update Status
            </button>
        </form>
    </div>
</div>

<!-- API Information -->
<div class="card">
    <h3>🔗 API Access</h3>
    <p>You can also access this incident via REST API:</p>
    <div style="background: #f8f9fa; padding: 1rem; border-radius: 4px; margin-top: 1rem; font-family: monospace;">
        <strong>GET</strong> ${pageContext.request.contextPath}/incidents/${incident.id}
    </div>
    <p style="margin-top: 1rem; color: #666; font-size: 0.9rem;">
        Use tools like curl, Postman, or any HTTP client to interact with the API endpoints.
    </p>
</div>

<%@ include file="../layout/footer.jsp" %>