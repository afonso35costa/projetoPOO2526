package utilizadores;

public class Admin extends Utilizador{

    public Admin (int id, String nome) {
        super(id, nome);
    }

    public Admin() {
        super();
    }

    public Admin(Admin outroAdmin) {
        super(outroAdmin);
    }

    @Override
    public Admin clone() {
        return new Admin(this);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        return super.equals(admin);
    }
}