@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> user = userService.findByUsername(authRequest.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(user.get().getUsername(), "", new ArrayList<>()));
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
