@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Vulnerable endpoint: directly concatenates user input into SQL
    @GetMapping("/search")
    public List<Map<String, Object>> searchUser(@RequestParam String name) {
        String query = "SELECT * FROM users WHERE name = '" + name + "'";
        return jdbcTemplate.queryForList(query);
    }
}
