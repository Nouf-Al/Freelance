package com.kgn.FreelanceProject.models;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min=3, message = "Title must be at least 3 characters.")
	private String title;

	@Size(min=3,message = "Description must be at least 3 characters.")
	private String description;

	@NotNull(message = "Offer end date is required.")
	@FutureOrPresent(message="Offer end date must be in future.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date offerEnd;

	@NotNull(message = "Duration is required.")
	@Min(value = 2, message="Duration must be at least 2 days.")
	private int duration;

	@NotNull(message = "Price is required.")
	@Min(value = 1, message = "Price must be more than 0.")
	private double price;
	
	@Value("${status.name:Open}")
	private String status;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id" )
	private Client client;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "freelancer_id")
	private Freelancer freelancer;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "freelancer_id"))
	private List<Freelancer> freelancers_like;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "client_likes", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	private List<Client> clients_like;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "offers", joinColumns = @JoinColumn(name = "freelancer_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Freelancer> freelancersOffers;

	@OneToMany(mappedBy="project", fetch= FetchType.LAZY)
    private List<Question> questions;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	public Project() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOfferEnd() {
		return offerEnd;
	}

	public void setOfferEnd(Date offerEnd) {
		this.offerEnd = offerEnd;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Freelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}

	public List<Freelancer> getFreelancers_like() {
		return freelancers_like;
	}

	public void setFreelancers_like(List<Freelancer> freelancers_like) {
		this.freelancers_like = freelancers_like;
	}
	public List<Client> getClients_like() {
		return clients_like;
	}

	public void setClients_like(List<Client> clients_like) {
		this.clients_like = clients_like;
	}

	public List<Freelancer> getFreelancersOffers() {
		return freelancersOffers;
	}

	public void setFreelancersOffers(List<Freelancer> freelancersOffer) {
		this.freelancersOffers = freelancersOffer;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Boolean isContain(Long id) {
		for (Freelancer u : freelancers_like) {
			if(u.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public Boolean isClientContain(Long id) {
		for (Client u : clients_like) {
			if(u.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean isOffer(Long id) {
		for (Freelancer u : freelancersOffers) {
			if(u.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public long calculateDaysLeft() {
		Date today = new Date();
		Timestamp timestamp = new Timestamp(today.getTime());
		long difference = (this.getOfferEnd().getTime()-timestamp.getTime())/86400000; //86400000 is the number of milliseconds
		if(difference<0){
			return 0;
		}
		return difference;
	}

	public boolean isOfferEndDate(){
		Date date = new Date();
		if (date.compareTo(this.getOfferEnd()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}