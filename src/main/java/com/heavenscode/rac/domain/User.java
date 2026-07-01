package com.heavenscode.rac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heavenscode.rac.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.BatchSize;

/**
 * A user.
 */
@Entity
@Table(name = "`Employee`", schema = "dbo")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "`ID`", nullable = false)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(name = "`UserName`", length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "`Password`", length = 255, nullable = false)
    private String password;

    @Size(max = 255)
    @Column(name = "`FullName`", length = 255)
    private String firstName;

    @Size(max = 255)
    @Column(name = "`Surname`", length = 255)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(name = "`Email`", length = 254, unique = true)
    private String email;

    @NotNull
    @Column(name = "`IsActive`", nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Transient
    private String langKey;

    @Size(max = 256)
    @Column(name = "`ImagePath`", length = 256)
    private String imageUrl;

    @Column(name = "`RoleId`")
    private Integer roleId;

    @Transient
    private String createdBy;

    @Transient
    private Instant createdDate;

    @Transient
    private String lastModifiedBy;

    @Transient
    private Instant lastModifiedDate;

    @Size(max = 20)
    @Transient
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Transient
    @JsonIgnore
    private String resetKey;

    @Transient
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "`ID`") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated='" + activated + '\'' +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                "}";
    }
}
