<%@ include file="../layout/header.jsp" %>

<div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h2>📋 Incident Dashboard</h2>
        <a href="${pageContext.request.contextPath}/web/incidents/new" class="btn btn-success">
            ➕ Create New Incident
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

    <!-- Statistics Cards -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-number">${stats.totalCount}</div>
            <div class="stat-label">Total Incidents</div>
        </div>
        <div class="stat-card">
            <div class="stat-number" style="color: #ffc107;">${stats.openCount}</div>
            <div class="stat-label">Open</div>
        </div>
        <div class="stat-card">
            <div class="stat-number" style="color: #007bff;">${stats.inProgressCount}</div>
            <div class="stat-label">In Progress</div>
        </div>
        <div class="stat-card">
            <div class="stat-number" style="color: #28a745;">${stats.resolvedCount}</div>
            <div class="stat-label">Resolved</div>
        </div>
    </div>

    <!-- Incidents Table -->
    <c:choose>
        <c:when test="${empty incidents}">
            <div style="text-align: center; padding: 3rem; color: #666;">
                <h3>📝 No incidents found</h3>
                <p>Create your first incident to get started!</p>
                <a href="${pageContext.request.contextPath}/web/incidents/new" class="btn" style="margin-top: 1rem;">
                    Create Incident
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Status</th>
                        <th>Priority</th>
                        <th>Created</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="incident" items="${incidents}">
                        <tr>
                            <td>#${incident.id}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/web/incidents/${incident.id}" 
                                   style="text-decoration: none; color: #667eea; font-weight: 500;">
                                    ${incident.title}
                                </a>
                            </td>
                            <td>
                                <span class="status-badge status-${incident.status.name().toLowerCase().replace('_', '-')}">
                                    ${incident.status.displayName}
                                </span>
                            </td>
                            <td>
                                <span class="priority-${incident.priority.name().toLowerCase()}">
                                    ⚡ ${incident.priority.displayName}
                                </span>
                            </td>
                            <td>
                                <fmt:formatDate value="${incident.createdAt}" pattern="MMM dd, yyyy HH:mm" />
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/web/incidents/${incident.id}" 
                                   class="btn" style="padding: 0.5rem 1rem; font-size: 0.875rem;">
                                    View
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<!-- API Information Card -->
<div class="card">
    <h3>🔗 API Endpoints</h3>
    <p>This application also provides REST API endpoints:</p>
    <ul style="margin-top: 1rem; padding-left: 2rem;">
        <li><strong>GET</strong> <code>/incidents</code> - Get all incidents</li>
        <li><strong>POST</strong> <code>/incidents</code> - Create new incident</li>
        <li><strong>GET</strong> <code>/incidents/{id}</code> - Get incident by ID</li>
        <li><strong>PUT</strong> <code>/incidents/{id}/status</code> - Update incident status</li>
        <li><strong>DELETE</strong> <code>/incidents/{id}</code> - Delete incident</li>
        <li><strong>GET</strong> <code>/health</code> - Health check</li>
    </ul>
</div>

<%@ include file="../layout/footer.jsp" %>